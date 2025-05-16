package com.proyecto.proyecto_renta.infrastructure.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.IToolService;
import com.proyecto.proyecto_renta.domain.entities.Tool;
import com.proyecto.proyecto_renta.infrastructure.repository.ToolRepository;

@Service
public class ToolServiceImpl implements IToolService {

    private final ToolRepository repository;

    public ToolServiceImpl(ToolRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Tool> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Tool> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Tool save(Tool tool) {
        return repository.save(tool);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

