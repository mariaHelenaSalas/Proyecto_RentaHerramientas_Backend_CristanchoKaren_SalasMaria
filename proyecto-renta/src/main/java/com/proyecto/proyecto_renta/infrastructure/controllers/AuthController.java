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
import com.proyecto.proyecto_renta.domain.exceptions.ConflictException;
import com.proyecto.proyecto_renta.infrastructure.repository.UserRepository;
import com.proyecto.proyecto_renta.infrastructure.security.JwtTokenProvider;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite todas las solicitudes CORS (ajusta en producción)
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
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken((UserDetails) authentication.getPrincipal());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("email", loginRequest.getEmail());
            response.put("role", authentication.getAuthorities().iterator().next().getAuthority());
            
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new ConflictException("El correo ya está registrado");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(registerRequest.getPassword()); // Asegúrate de que se hashea en el servicio
        user.setRole(registerRequest.getRole());

        User registeredUser = registerUserUseCase.registerUser(user);

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

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromEntity(registeredUser));
    }
}