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
import com.proyecto.proyecto_renta.domain.entities.Provider;
import com.proyecto.proyecto_renta.domain.entities.User;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    @Autowired
    private IProviderService service;

    // Solo para administradores para listar todos los proveedores
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Provider>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    // Solo para administradores para obtener un proveedor específico
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Provider> getOne(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Solo para administradores para crear un proveedor
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Provider> create(@RequestBody Provider provider) {
        return ResponseEntity.ok(service.save(provider));
    }

    // Solo para administradores para eliminar un proveedor
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // El proveedor puede acceder a su propio perfil
    @GetMapping("/profile")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Provider> getProviderProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return service.findByUserId(user.getIdUser())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // El proveedor puede actualizar su propio perfil
    @PutMapping("/profile")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Provider> updateProfile(@RequestBody Provider provider) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return service.findByUserId(user.getIdUser())
                .map(existingProvider -> {
                    // Actualizar sólo los campos permitidos (no el usuario ni el ID)
                    existingProvider.setPhone(provider.getPhone());
                    existingProvider.setAddress(provider.getAddress());
                    return ResponseEntity.ok(service.save(existingProvider));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
