package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.Tool;

public interface IToolService {
    List<Tool> findAll();
    Optional<Tool> findById(Long id);
    Tool save(Tool tool); 
    void deleteById(Long id);
    List<Tool> findByProviderId(Long providerId);
}
