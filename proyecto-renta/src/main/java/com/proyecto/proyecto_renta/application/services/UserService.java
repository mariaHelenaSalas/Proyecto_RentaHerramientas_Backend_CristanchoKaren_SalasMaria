package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.User;

public interface UserService {
    Optional<User> findOneByUsername(String username);
    boolean existsByEmail(String email);
    User save(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    void deleteById(Long id);
    User update(User user);
    Optional<User> findByEmail(String email);
    boolean existsById(Long id);
}
