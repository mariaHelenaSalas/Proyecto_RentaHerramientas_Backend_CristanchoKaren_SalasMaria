package com.proyecto.proyecto_renta.application.usecase;


import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.infrastructure.repository.UserRepository;

@Service
public class DeleteUserUseCase {
    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
