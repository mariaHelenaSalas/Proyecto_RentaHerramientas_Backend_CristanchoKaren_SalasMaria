package com.proyecto.proyecto_renta.infrastructure.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.IUserService;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.infrastructure.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        // Solo codificamos la contraseña si es un usuario nuevo o si se está cambiando la contraseña
        if (user.getIdUser() == null || isPasswordChanged(user)) {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        }
        return repository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private boolean isPasswordChanged(User user) {
        if (user.getIdUser() == null) return true;
        
        Optional<User> existingUser = repository.findById(user.getIdUser());
        return existingUser.isPresent() && 
               !existingUser.get().getPasswordHash().equals(user.getPasswordHash());
    }
}