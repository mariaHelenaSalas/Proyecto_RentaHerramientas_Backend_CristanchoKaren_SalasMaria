package com.proyecto.proyecto_renta.infrastructure.repositories.Tool;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.ToolService;
import com.proyecto.proyecto_renta.domain.entities.Tool;

import java.util.List;
import java.util.Optional;

@Service
public class ToolServiceImpl implements ToolService {

    private final ToolRepository toolRepository;

    public ToolServiceImpl(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }
    @Override
    public List<Tool> findAll() {
        return toolRepository.findAll();
    }

    @Override
    public Optional<Tool> findById(Long id) {
        return toolRepository.findById(id);
    }

    @Override
    public Tool save(Tool tool) {
        return toolRepository.save(tool);
    }

    @Override
    public void deleteById(Long id) {
        toolRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return toolRepository.existsById(id);
    }
}
