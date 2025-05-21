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

import com.proyecto.proyecto_renta.application.services.IInvoiceService;
import com.proyecto.proyecto_renta.domain.entities.Invoice;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.domain.exceptions.ForbiddenException;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private IInvoiceService service;

    // El administrador puede ver todas las facturas
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Invoice>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    // Obtenga una factura específica con control de acceso basado en funciones
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'PROVIDER')")
    public ResponseEntity<Invoice> getOne(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Invoice invoice = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
        
        // Los clientes sólo pueden ver sus propias facturas
        if (user.getRole() == User.Role.CLIENT && 
            !invoice.getPayment().getReservation().getClient().getUser().getIdUser().equals(user.getIdUser())) {
            throw new ForbiddenException("You don't have permission to view this invoice");
        }
        
        // Los proveedores solo pueden ver las facturas de sus herramientas
        if (user.getRole() == User.Role.PROVIDER && 
            !invoice.getPayment().getReservation().getTool().getProvider().getUser().getIdUser().equals(user.getIdUser())) {
            throw new ForbiddenException("You don't have permission to view this invoice");
        }
        
        return ResponseEntity.ok(invoice);
    }

    // Crear factura (solo administrador o sistema)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Invoice> create(@Valid @RequestBody Invoice invoice) {
        return ResponseEntity.ok(service.save(invoice));
    }

    // El administrador puede eliminar una factura
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
        
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Los proveedores pueden ver las facturas de sus herramientas
    @GetMapping("/provider-invoices")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<Invoice>> getProviderInvoices() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return ResponseEntity.ok(service.findByProviderUserId(user.getIdUser()));
    }
    
    // Los clientes pueden ver sus propias facturas
    @GetMapping("/my-invoices")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Invoice>> getClientInvoices() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return ResponseEntity.ok(service.findByClientUserId(user.getIdUser()));
    }
}
