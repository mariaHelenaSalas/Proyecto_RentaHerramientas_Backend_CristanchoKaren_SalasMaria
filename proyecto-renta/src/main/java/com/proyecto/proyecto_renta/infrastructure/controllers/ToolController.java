package com.proyecto.proyecto_renta.infrastructure.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.proyecto.proyecto_renta.application.services.ToolService;
import com.proyecto.proyecto_renta.domain.dtos.ToolDto;
import com.proyecto.proyecto_renta.domain.entities.Tool;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolController {

    @Autowired
    private ToolService toolService;

    @GetMapping
    public ResponseEntity<List<Tool>> listAvailableTools() {
        return ResponseEntity.ok(toolService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolDto> getTool(@PathVariable Long id) {
        return toolService.findById(id)
                .map(tool -> new ToolDto(tool))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPPLIER', 'ADMIN')")
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool) {
        Tool created = toolService.save(tool);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPPLIER', 'ADMIN')")
    public ResponseEntity<Tool> update(@PathVariable Long id, @RequestBody Tool tool) {
        if (!toolService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tool.setId(id);
        Tool updatedTool = toolService.save(tool);
        return ResponseEntity.ok(updatedTool);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPPLIER', 'ADMIN')")
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        toolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
