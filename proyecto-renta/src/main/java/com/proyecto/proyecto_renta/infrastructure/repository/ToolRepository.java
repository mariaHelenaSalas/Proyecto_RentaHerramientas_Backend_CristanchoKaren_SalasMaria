package com.proyecto.proyecto_renta.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.proyecto_renta.domain.entities.Tool;

public interface ToolRepository extends CrudRepository<Tool, Long> {
    List<Tool> findByStatus(Tool.Status status);
}
