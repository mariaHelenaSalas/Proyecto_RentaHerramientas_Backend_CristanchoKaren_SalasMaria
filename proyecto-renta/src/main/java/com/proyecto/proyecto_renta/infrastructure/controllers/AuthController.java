// package com.proyecto.proyecto_renta.infrastructure.controllers;


// import com.rental.toolrental.dto.auth.JwtResponse;
// import com.rental.toolrental.dto.auth.LoginRequest;
// import com.rental.toolrental.dto.auth.SignupRequest;
// import com.rental.toolrental.service.AuthService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api/auth")
// @Tag(name = "Authentication", description = "Authentication API")
// public class AuthController {

//     @Autowired
//     private AuthService authService;

//     @PostMapping("/login")
//     @Operation(summary = "Authenticate user and generate JWT token")
//     public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//         JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
//         return ResponseEntity.ok(jwtResponse);
//     }

//     @PostMapping("/signup")
//     @Operation(summary = "Register a new user")
//     public ResponseEntity<JwtResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
//         JwtResponse jwtResponse = authService.registerUser(signupRequest);
//         return ResponseEntity.ok(jwtResponse);
//     }
// }
