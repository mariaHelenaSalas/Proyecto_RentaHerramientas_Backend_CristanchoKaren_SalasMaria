package com.proyecto.proyecto_renta.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.proyecto_renta.domain.dto.LoginRequest;
import com.proyecto.proyecto_renta.domain.entities.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails);
        
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", token);
        responseBody.put("email", userDetails.getUsername());
        
        if (userDetails instanceof User) {
            User user = (User) userDetails;
            responseBody.put("role", user.getRole().name());
            responseBody.put("userId", user.getIdUser());
            responseBody.put("name", user.getName());
        } else {
            String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse("UNKNOWN");
            responseBody.put("role", role);
        }
        
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }
}