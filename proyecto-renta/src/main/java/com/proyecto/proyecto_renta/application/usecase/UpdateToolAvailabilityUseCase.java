package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.Tool;
import com.proyecto.proyecto_renta.infrastructure.repository.ToolRepository;

import org.springframework.stereotype.Service;

@Service
public class UpdateToolAvailabilityUseCase {
    private final ToolRepository toolRepository;

    public UpdateToolAvailabilityUseCase(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public Tool updateAvailability(Long toolId, Tool.Status status) {
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new IllegalArgumentException("Herramienta no encontrada."));
        tool.setStatus(status);
        return toolRepository.save(tool);
    }
}
