package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.dtos.Report;

public interface ReportService {
    List<Report> findAll();
    Optional<Report> findById(Long id);
    Report save(Report report);
    void deleteById(Long id);
    boolean existsById(Long id);
}
