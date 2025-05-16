package com.proyecto.proyecto_renta.infrastructure.repository;

import com.proyecto.proyecto_renta.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
}
