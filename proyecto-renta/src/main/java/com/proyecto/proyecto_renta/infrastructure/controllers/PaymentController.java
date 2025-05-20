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

import com.proyecto.proyecto_renta.application.services.IPaymentService;
import com.proyecto.proyecto_renta.application.usecase.ProcessPaymentUseCase;
import com.proyecto.proyecto_renta.domain.entities.Payment;
import com.proyecto.proyecto_renta.domain.entities.Reservation;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.domain.exceptions.ForbiddenException;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;
    
    @Autowired
    private ProcessPaymentUseCase processPaymentUseCase;

    // El administrador puede ver todos los pagos
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Payment>> list() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    // Obtenga un pago específico con control de acceso basado en funciones
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'PROVIDER')")
    public ResponseEntity<Payment> getOne(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Payment payment = paymentService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        
        // Los clientes sólo pueden ver sus propios pagos
        if (user.getRole() == User.Role.CLIENT && 
            !payment.getReservation().getClient().getUser().getIdUser().equals(user.getIdUser())) {
            throw new ForbiddenException("You don't have permission to view this payment");
        }
        
        // Los proveedores sólo pueden ver los pagos de sus herramientas
        if (user.getRole() == User.Role.PROVIDER && 
            !payment.getReservation().getTool().getProvider().getUser().getIdUser().equals(user.getIdUser())) {
            throw new ForbiddenException("You don't have permission to view this payment");
        }
        
        return ResponseEntity.ok(payment);
    }

    // Sólo los clientes pueden crear pagos para sus reservas
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Payment> create(@Valid @RequestBody Payment payment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        // Comprobar que la reserva existe y pertenece al cliente
        Reservation reservation = payment.getReservation();
        if (reservation == null || reservation.getIdReservation() == null) {
            throw new ResourceNotFoundException("Reservation is required");
        }
        
        // Procesar el pago utilizando el caso de uso
        return ResponseEntity.ok(processPaymentUseCase.processPayment(payment));
    }

    // El administrador puede eliminar cualquier pago
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        
        paymentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Los clientes pueden ver sus propios pagos
    @GetMapping("/my-payments")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Payment>> getClientPayments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return ResponseEntity.ok(paymentService.findByClientUserId(user.getIdUser()));
    }
    
    // Los proveedores pueden ver los pagos de sus herramientas
    @GetMapping("/provider-payments")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<Payment>> getProviderPayments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return ResponseEntity.ok(paymentService.findByProviderUserId(user.getIdUser()));
    }
}
