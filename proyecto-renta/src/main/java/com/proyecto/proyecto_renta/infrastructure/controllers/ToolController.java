package com.proyecto.proyecto_renta.infrastructure.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto_renta.application.services.IProviderService;
import com.proyecto.proyecto_renta.application.services.IToolService;
import com.proyecto.proyecto_renta.domain.entities.Provider;
import com.proyecto.proyecto_renta.domain.entities.Tool;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.domain.exceptions.ForbiddenException;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tools")
public class ToolController {

    @Autowired
    private IToolService toolService;
    
    @Autowired
    private IProviderService providerService;

    // Cualquier persona autentificada puede listar todas las herramientas
    @GetMapping
    public ResponseEntity<List<Tool>> list() {
        return ResponseEntity.ok(toolService.findAll());
    }

    // Cualquier persona autentificada puede obtener una herramienta específica
    @GetMapping("/{id}")
    public ResponseEntity<Tool> getOne(@PathVariable Long id) {
        Tool tool = toolService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tool", "id", id));
        return ResponseEntity.ok(tool);
    }

    // Sólo los proveedores pueden crear herramientas
    @PostMapping
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Tool> create(@Valid @RequestBody Tool tool) {
        // Establecer el proveedor en el perfil de proveedor del usuario que ha iniciado sesión.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Provider provider = providerService.findByUserId(user.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Provider profile not found for user id " + user.getIdUser()));
        
        tool.setProvider(provider);
        return ResponseEntity.ok(toolService.save(tool));
    }

    // Sólo los proveedores pueden actualizar sus propias herramientas
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Tool> update(@PathVariable Long id, @Valid @RequestBody Tool tool) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Tool existingTool = toolService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tool", "id", id));
        
        // Comprobar si la herramienta pertenece al proveedor actual
        if (!existingTool.getProvider().getUser().getIdUser().equals(user.getIdUser()) && 
            !user.getRole().equals(User.Role.ADMIN)) {
            throw new ForbiddenException("No tiene permiso para actualizar esta herramienta");
        }
        
        // Actualizar los campos de la herramienta
        existingTool.setName(tool.getName());
        existingTool.setDescription(tool.getDescription());
        existingTool.setRentalCost(tool.getRentalCost());
        existingTool.setStatus(tool.getStatus());
        existingTool.setImageUrl(tool.getImageUrl());
        existingTool.setCategory(tool.getCategory());
        
        return ResponseEntity.ok(toolService.save(existingTool));
    }

    // Solo los proveedores pueden eliminar sus propias herramientas
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Tool existingTool = toolService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tool", "id", id));
        
        // Comprobar si la herramienta pertenece al proveedor actual
        if (!existingTool.getProvider().getUser().getIdUser().equals(user.getIdUser()) && 
            !user.getRole().equals(User.Role.ADMIN)) {
            throw new ForbiddenException("You don't have permission to delete this tool");
        }
        
        toolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // El proveedor puede listar solo sus herramientas
    @GetMapping("/my-tools")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<Tool>> getProviderTools() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Provider provider = providerService.findByUserId(user.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Provider profile not found for user id " + user.getIdUser()));
        
        return ResponseEntity.ok(toolService.findByProviderId(provider.getIdProvider()));
    }
}
