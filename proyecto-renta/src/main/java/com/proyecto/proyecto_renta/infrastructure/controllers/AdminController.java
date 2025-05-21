package com.proyecto.proyecto_renta.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto_renta.application.services.IClientService;
import com.proyecto.proyecto_renta.application.services.IPaymentService;
import com.proyecto.proyecto_renta.application.services.IProviderService;
import com.proyecto.proyecto_renta.application.services.IReservationService;
import com.proyecto.proyecto_renta.application.services.IToolService;
import com.proyecto.proyecto_renta.application.services.IUserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IClientService clientService;
    
    @Autowired
    private IProviderService providerService;
    
    @Autowired
    private IToolService toolService;
    
    @Autowired
    private IReservationService reservationService;
    
    @Autowired
    private IPaymentService paymentService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalUsers = userService.findAll().size();
        long totalClients = clientService.findAll().size();
        long totalProviders = providerService.findAll().size();
        
        long totalTools = toolService.findAll().size();
        long totalReservations = reservationService.findAll().size();
        long totalPayments = paymentService.findAll().size();
        
        stats.put("totalUsers", totalUsers);
        stats.put("totalClients", totalClients);
        stats.put("totalProviders", totalProviders);
        stats.put("totalTools", totalTools);
        stats.put("totalReservations", totalReservations);
        stats.put("totalPayments", totalPayments);
        
        return ResponseEntity.ok(stats);
    }
}
