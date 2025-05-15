package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.Tool;
import com.proyecto.proyecto_renta.infrastructure.repository.ToolRepository;

import org.springframework.stereotype.Service;

@Service
public class AddToolUseCase {
    private final ToolRepository toolRepository;

    public AddToolUseCase(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public Tool addTool(Tool tool) {
        return toolRepository.save(tool);
    }
}