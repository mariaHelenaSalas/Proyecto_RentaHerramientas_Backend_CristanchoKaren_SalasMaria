package com.proyecto.proyecto_renta.infrastructure.repositories.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.proyecto_renta.domain.entities.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
