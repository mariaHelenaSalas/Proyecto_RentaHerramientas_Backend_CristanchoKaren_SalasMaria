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
import com.proyecto.proyecto_renta.application.services.IReservationService;
import com.proyecto.proyecto_renta.application.services.IToolService;
import com.proyecto.proyecto_renta.application.usecase.CreateReservationUseCase;
import com.proyecto.proyecto_renta.domain.entities.Client;
import com.proyecto.proyecto_renta.domain.entities.Reservation;
import com.proyecto.proyecto_renta.domain.entities.Tool;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.domain.exceptions.BadRequestException;
import com.proyecto.proyecto_renta.domain.exceptions.ForbiddenException;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;
    
    @Autowired
    private IClientService clientService;
    
    @Autowired
    private IToolService toolService;
    
    @Autowired
    private CreateReservationUseCase createReservationUseCase;

    // El administrador puede ver todas las reservas
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> list() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    // Obtenga una reserva específica con control de acceso basado en funciones
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'PROVIDER')")
    public ResponseEntity<Reservation> getOne(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Reservation reservation = reservationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        
        // Los clientes sólo pueden ver sus propias reservas
        if (user.getRole() == User.Role.CLIENT && 
            !reservation.getClient().getUser().getIdUser().equals(user.getIdUser())) {
            throw new ForbiddenException("You don't have permission to view this reservation");
        }
        
        // Los proveedores sólo pueden ver las reservas de sus herramientas
        if (user.getRole() == User.Role.PROVIDER && 
            !reservation.getTool().getProvider().getUser().getIdUser().equals(user.getIdUser())) {
            throw new ForbiddenException("You don't have permission to view this reservation");
        }
        
        return ResponseEntity.ok(reservation);
    }

    // Sólo los clientes pueden crear reservas
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Reservation> create(@Valid @RequestBody Reservation reservation) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        // Obtener el perfil de cliente del usuario actual
        Client client = clientService.findByUserId(user.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Client profile not found for user id " + user.getIdUser()));
        
        // Verificar la existencia de la herramienta
        Tool tool = toolService.findById(reservation.getTool().getIdTool())
                .orElseThrow(() -> new ResourceNotFoundException("Tool", "id", reservation.getTool().getIdTool()));
        
        // Configurar el cliente
        reservation.setClient(client);
        
        try {
            // Utilice CreateReservationUseCase para gestionar la lógica empresarial
            return ResponseEntity.ok(createReservationUseCase.createReservation(reservation));
        } catch (IllegalStateException e) {
            throw new BadRequestException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    // El administrador puede actualizar cualquier reserva, los clientes sólo pueden actualizar las suyas.
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Reservation> update(@PathVariable Long id, @Valid @RequestBody Reservation reservation) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Reservation existingReservation = reservationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        
        // Los clientes sólo pueden actualizar sus propias reservas
        if (user.getRole() == User.Role.CLIENT && 
            !existingReservation.getClient().getUser().getIdUser().equals(user.getIdUser())) {
            throw new ForbiddenException("You don't have permission to update this reservation");
        }
        
        // Actualizar sólo los campos permitidos en función del estado
        if (existingReservation.getStatus() == Reservation.Status.PENDING) {
            existingReservation.setStartDate(reservation.getStartDate());
            existingReservation.setEndDate(reservation.getEndDate());
            existingReservation.setStatus(reservation.getStatus());
        } else if (user.getRole() == User.Role.ADMIN) {
            // El administrador puede actualizar el estado aunque no esté pendiente
            existingReservation.setStatus(reservation.getStatus());
        } else {
            throw new BadRequestException("Cannot update reservation that is not in PENDING status");
        }
        
        return ResponseEntity.ok(reservationService.save(existingReservation));
    }

    // El administrador puede eliminar cualquier reserva, los clientes sólo pueden cancelar las suyas.
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Reservation existingReservation = reservationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        
        // Los clientes sólo pueden eliminar sus propias reservas
        if (user.getRole() == User.Role.CLIENT && 
            !existingReservation.getClient().getUser().getIdUser().equals(user.getIdUser())) {
            throw new ForbiddenException("You don't have permission to delete this reservation");
        }
        
        // Para los clientes, sólo permitimos la cancelación si la reserva sigue pendiente
        if (user.getRole() == User.Role.CLIENT && 
            existingReservation.getStatus() != Reservation.Status.PENDING) {
            throw new BadRequestException("Cannot cancel reservation that is not in PENDING status");
        }
        
        // Para admin, permitimos la eliminación independientemente del estado
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Los clientes pueden ver sus propias reservas
    @GetMapping("/my-reservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Reservation>> getClientReservations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Client client = clientService.findByUserId(user.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Client profile not found for user id " + user.getIdUser()));
        
        return ResponseEntity.ok(reservationService.findByClientId(client.getIdClient()));
    }
    
    // Los proveedores pueden ver las reservas de sus herramientas
    @GetMapping("/provider-reservations")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<Reservation>> getProviderReservations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        return ResponseEntity.ok(reservationService.findByProviderUserId(user.getIdUser()));
    }
}
