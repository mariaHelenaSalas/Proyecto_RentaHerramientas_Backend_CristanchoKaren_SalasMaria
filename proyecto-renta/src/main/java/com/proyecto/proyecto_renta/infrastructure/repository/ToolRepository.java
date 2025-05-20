package com.proyecto.proyecto_renta.infrastructure.repository;

import com.proyecto.proyecto_renta.domain.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    List<Tool> findByProviderIdProvider(Long providerId);
}
