package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.Tool;

public interface IToolService {
    List<Tool> findAll();
    Tool findById(Long id);
    Tool save(Tool tool); 
    void deleteById(Long id);
}
