package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.User;

public interface IUserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    Optional<User> findByEmail(String email);
}
