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

import com.proyecto.proyecto_renta.application.services.IClientService;
import com.proyecto.proyecto_renta.domain.entities.Client;
import com.proyecto.proyecto_renta.domain.entities.User;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private IClientService service;

    // Solo para administradores para listar todos los clientes
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Client>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    // Solo para administradores para obtener un cliente específico
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> getOne(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Solo para administradores para crear un cliente
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> create(@RequestBody Client client) {
        return ResponseEntity.ok(service.save(client));
    }

    // Solo para administradores para eliminar un cliente
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // El cliente puede acceder a su propio perfil
    @GetMapping("/profile")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Client> getClientProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return service.findByUserId(user.getIdUser())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // El cliente puede actualizar su propio perfil
    @PutMapping("/profile")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Client> updateProfile(@RequestBody Client client) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return service.findByUserId(user.getIdUser())
                .map(existingClient -> {
                    // Actualizar sólo los campos permitidos (no el usuario ni el ID)
                    existingClient.setPhone(client.getPhone());
                    existingClient.setAddress(client.getAddress());
                    return ResponseEntity.ok(service.save(existingClient));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
