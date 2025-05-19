package com.proyecto.proyecto_renta.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto_renta.application.usecase.GenerateProfitReportUseCase;
import com.proyecto.proyecto_renta.domain.dto.ProfitReportDTO;
import com.proyecto.proyecto_renta.domain.dto.ToolUsageReport;
import com.proyecto.proyecto_renta.domain.entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private GenerateProfitReportUseCase generateProfitReportUseCase;

    // El administrador puede ver el informe general de beneficios
    @GetMapping("/profit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfitReportDTO> getProfitReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        BigDecimal totalProfit = generateProfitReportUseCase.generateProfitReport(startDate, endDate);
        
        ProfitReportDTO report = new ProfitReportDTO();
        report.setTotalProfit(totalProfit);
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setGeneratedAt(LocalDate.now());
        
        return ResponseEntity.ok(report);
    }
    
    // El proveedor puede ver su propio informe de beneficios
    @GetMapping("/provider-profit")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<ProfitReportDTO> getProviderProfitReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        BigDecimal providerProfit = generateProfitReportUseCase.generateProviderProfitReport(user.getIdUser(), startDate, endDate);
        
        ProfitReportDTO report = new ProfitReportDTO();
        report.setTotalProfit(providerProfit);
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setGeneratedAt(LocalDate.now());
        
        return ResponseEntity.ok(report);
    }
    
    // El administrador puede ver el informe de uso de la herramienta
    @GetMapping("/tool-usage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ToolUsageReport>> getToolUsageReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<ToolUsageReport> report = generateProfitReportUseCase.generateToolUsageReport(startDate, endDate);
        
        return ResponseEntity.ok(report);
    }
    
    // El proveedor puede ver su propio informe de uso de la herramienta
    @GetMapping("/provider-tool-usage")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<ToolUsageReport>> getProviderToolUsageReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        List<ToolUsageReport> report = generateProfitReportUseCase.generateProviderToolUsageReport(user.getIdUser(), startDate, endDate);
        
        return ResponseEntity.ok(report);
    }
}
