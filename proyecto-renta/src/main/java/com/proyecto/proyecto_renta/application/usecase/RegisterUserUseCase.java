package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.infrastructure.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {
    private final UserRepository userRepository;

    public RegisterUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }
        return userRepository.save(user);
    }
}
