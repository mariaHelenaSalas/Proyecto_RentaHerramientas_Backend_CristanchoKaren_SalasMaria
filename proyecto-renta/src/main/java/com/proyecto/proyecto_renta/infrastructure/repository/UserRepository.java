package com.proyecto.proyecto_renta.infrastructure.repository;


import com.proyecto.proyecto_renta.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
