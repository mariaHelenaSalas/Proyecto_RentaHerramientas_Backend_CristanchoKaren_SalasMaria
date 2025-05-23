package com.proyecto.proyecto_renta.infrastructure.repositories.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.proyecto_renta.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
