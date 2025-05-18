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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto_renta.application.services.IReturnService;
import com.proyecto.proyecto_renta.application.usecase.RegisterReturnUseCase;
import com.proyecto.proyecto_renta.domain.entities.Return;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.domain.exceptions.ForbiddenException;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/returns")
public class ReturnController {

    @Autowired
    private IReturnService returnService;
    
    @Autowired
    private RegisterReturnUseCase registerReturnUseCase;

    // El administrador puede ver todas las devoluciones
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Return>> list() {
        return ResponseEntity.ok(returnService.findAll());
    }

    // Obtenga un rendimiento específico con el control de acceso basado en funciones
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'PROVIDER')")
    public ResponseEntity<Return> getOne(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Return returnRecord = returnService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Return", "id", id));
        
        // Los clientes sólo pueden ver sus propias declaraciones
        if (user.getRole() == User.Role.CLIENT && 
            !returnRecord.getReservation().getClient().getUser().getIdUser().equals(user.getIdUser())) {
            throw new ForbiddenException("You don't have permission to view this return");
        }
        
        // Los proveedores sólo pueden ver los rendimientos de sus herramientas
        if (user.getRole() == User.Role.PROVIDER && 
            !returnRecord.getReservation().getTool().getProvider().getUser().getIdUser().equals(user.getIdUser())) {
            throw new ForbiddenException("You don't have permission to view this return");
        }
        
        return ResponseEntity.ok(returnRecord);
    }

    // Registrar una devolución (proveedor o administrador)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    public ResponseEntity<Return> create(@Valid @RequestBody Return returnRecord) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        // Si es el proveedor, compruebe que es el propietario de la herramienta
        if (user.getRole() == User.Role.PROVIDER) {
            // Se comprobará en el caso de uso
        }
        
        // Procesar la devolución utilizando el caso de uso
        return ResponseEntity.ok(registerReturnUseCase.registerReturn(returnRecord));
    }

    // El administrador puede eliminar un registro de devolución
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        returnService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Return", "id", id));
        
        returnService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Los proveedores pueden consultar los rendimientos de sus herramientas
    @GetMapping("/provider-returns")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<Return>> getProviderReturns() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return ResponseEntity.ok(returnService.findByProviderUserId(user.getIdUser()));
    }
    
    // Los clientes pueden ver sus propias declaraciones
    @GetMapping("/my-returns")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Return>> getClientReturns() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return ResponseEntity.ok(returnService.findByClientUserId(user.getIdUser()));
    }
}
