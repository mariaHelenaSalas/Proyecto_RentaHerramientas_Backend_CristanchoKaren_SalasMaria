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

import com.proyecto.proyecto_renta.application.services.INotificationService;
import com.proyecto.proyecto_renta.domain.entities.Notification;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.domain.exceptions.ForbiddenException;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private INotificationService service;

    // El administrador puede ver todas las notificaciones
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Notification>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    // Obtenga una notificación específica con control de acceso basado en funciones
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'PROVIDER')")
    public ResponseEntity<Notification> getOne(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Notification notification = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        
        // Los usuarios sólo pueden ver sus propias notificaciones
        if (!notification.getUser().getIdUser().equals(user.getIdUser()) && 
            user.getRole() != User.Role.ADMIN) {
            throw new ForbiddenException("You don't have permission to view this notification");
        }
        
        return ResponseEntity.ok(notification);
    }

    // Crear notificación (sólo para administradores)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Notification> create(@Valid @RequestBody Notification notification) {
        return ResponseEntity.ok(service.save(notification));
    }

    // Marcar notificación como leída
    @PutMapping("/{id}/read")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'PROVIDER')")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Notification notification = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        
        // Los usuarios sólo pueden marcar sus propias notificaciones como leídas
        if (!notification.getUser().getIdUser().equals(user.getIdUser()) && 
            user.getRole() != User.Role.ADMIN) {
            throw new ForbiddenException("You don't have permission to update this notification");
        }
        
        notification.setRead(true);
        return ResponseEntity.ok(service.save(notification));
    }

    // Borrar notificación
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'PROVIDER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Notification notification = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        
        // Los usuarios sólo pueden borrar sus propias notificaciones
        if (!notification.getUser().getIdUser().equals(user.getIdUser()) && 
            user.getRole() != User.Role.ADMIN) {
            throw new ForbiddenException("You don't have permission to delete this notification");
        }
        
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Obtener las notificaciones del usuario actual
    @GetMapping("/my-notifications")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'PROVIDER')")
    public ResponseEntity<List<Notification>> getMyNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return ResponseEntity.ok(service.findByUserId(user.getIdUser()));
    }
    
    // Obtener notificaciones no leídas para el usuario actual
    @GetMapping("/unread")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'PROVIDER')")
    public ResponseEntity<List<Notification>> getUnreadNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return ResponseEntity.ok(service.findUnreadByUserId(user.getIdUser()));
    }
    
    // Marcar todas las notificaciones como leídas para el usuario actual
    @PutMapping("/mark-all-read")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'PROVIDER')")
    public ResponseEntity<Void> markAllAsRead() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        service.markAllAsRead(user.getIdUser());
        return ResponseEntity.ok().build();
    }
}
