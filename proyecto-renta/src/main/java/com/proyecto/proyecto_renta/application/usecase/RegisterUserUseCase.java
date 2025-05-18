package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.application.services.IUserService;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.domain.exceptions.BadRequestException;
import com.proyecto.proyecto_renta.domain.exceptions.ConflictException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUserUseCase {
    private final IUserService userService;

    public RegisterUserUseCase(IUserService userService) {
        this.userService = userService;
    }

    @Transactional
    public User registerUser(User user) {
        // Validate required fields
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new BadRequestException("Name is required");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new BadRequestException("Email is required");
        }
        
        if (user.getPasswordHash() == null || user.getPasswordHash().trim().isEmpty()) {
            throw new BadRequestException("Password is required");
        }
        
        if (user.getRole() == null) {
            throw new BadRequestException("Role is required");
        }
        
        // Check if email is already registered
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            throw new ConflictException("Email is already registered");
        }
        
        return userService.save(user);
    }
}
