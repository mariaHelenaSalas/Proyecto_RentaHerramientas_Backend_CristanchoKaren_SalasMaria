package com.proyecto.proyecto_renta.infrastructure.controllers;

import com.proyecto.proyecto_renta.application.services.IClientService;
import com.proyecto.proyecto_renta.application.services.IProviderService;
import com.proyecto.proyecto_renta.application.usecase.RegisterUserUseCase;
import com.proyecto.proyecto_renta.domain.dto.LoginRequest;
import com.proyecto.proyecto_renta.domain.dto.RegisterRequest;
import com.proyecto.proyecto_renta.domain.dto.UserResponse;
import com.proyecto.proyecto_renta.domain.entities.Client;
import com.proyecto.proyecto_renta.domain.entities.Provider;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.domain.exceptions.BadRequestException;
import com.proyecto.proyecto_renta.domain.exceptions.ConflictException;
import com.proyecto.proyecto_renta.infrastructure.repository.UserRepository;
import com.proyecto.proyecto_renta.infrastructure.security.JwtTokenProvider;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RegisterUserUseCase registerUserUseCase;
    private final UserRepository userRepository;
    private final IClientService clientService;
    private final IProviderService providerService;

    public AuthController(AuthenticationManager authenticationManager,
                         JwtTokenProvider jwtTokenProvider,
                         RegisterUserUseCase registerUserUseCase,
                         UserRepository userRepository,
                         IClientService clientService,
                         IProviderService providerService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.registerUserUseCase = registerUserUseCase;
        this.userRepository = userRepository;
        this.clientService = clientService;
        this.providerService = providerService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(userDetails);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("email", userDetails.getUsername());
            
            // Añadir rol de usuario
            if (userDetails instanceof User) {
                User user = (User) userDetails;
                response.put("role", user.getRole().name());
                response.put("userId", user.getIdUser());
                response.put("name", user.getName());
            } else {
                // Obtener el rol de las autoridades
                String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(a -> a.getAuthority().replace("ROLE_", ""))
                    .orElse("UNKNOWN");
                response.put("role", role);
            }
            
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // Verificar si el correo existe
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new ConflictException("Email is already registered");
        }
        
        // Validar rol
        if (registerRequest.getRole() == null) {
            throw new BadRequestException("Role is required");
        }
        
        // Crear usuario
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(registerRequest.getPassword());
        user.setRole(registerRequest.getRole());
        
        // Save user
        User registeredUser = registerUserUseCase.registerUser(user);
        
        // Crear cliente o proveedor basado en el rol
        if (registerRequest.getRole() == User.Role.CLIENT) {
            Client client = new Client();
            client.setUser(registeredUser);
            client.setPhone(registerRequest.getPhone());
            client.setAddress(registerRequest.getAddress());
            clientService.save(client);
        } else if (registerRequest.getRole() == User.Role.PROVIDER) {
            Provider provider = new Provider();
            provider.setUser(registeredUser);
            provider.setPhone(registerRequest.getPhone());
            provider.setAddress(registerRequest.getAddress());
            providerService.save(provider);
        }
        
        return ResponseEntity.ok(UserResponse.fromEntity(registeredUser));
    }
}
