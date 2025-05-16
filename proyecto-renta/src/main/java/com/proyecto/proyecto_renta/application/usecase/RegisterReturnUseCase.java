package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.Return;
import com.proyecto.proyecto_renta.domain.entities.Tool;
import com.proyecto.proyecto_renta.infrastructure.repository.ReturnRepository;
import com.proyecto.proyecto_renta.infrastructure.repository.ToolRepository;

import org.springframework.stereotype.Service;

@Service
public class RegisterReturnUseCase {
    private final ReturnRepository returnRepository;
    private final ToolRepository toolRepository;

    public RegisterReturnUseCase(ReturnRepository returnRepository, ToolRepository toolRepository) {
        this.returnRepository = returnRepository;
        this.toolRepository = toolRepository;
    }

    public Return registerReturn(Return returnRecord) {
        Tool tool = toolRepository.findById(returnRecord.getReservation().getTool().getIdTool())
                .orElseThrow(() -> new IllegalArgumentException("Herramienta no encontrada."));

        // Actualizar estado de la herramienta
        tool.setStatus(Tool.Status.AVAILABLE);
        toolRepository.save(tool);

        return returnRepository.save(returnRecord);
    }
}
