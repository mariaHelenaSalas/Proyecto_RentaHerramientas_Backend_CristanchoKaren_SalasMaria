package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.History;

public interface IHistoryService {
    List<History> findAll();
    Optional<History> findById(Long id); 
    History save(History history);
    void deleteById(Long id);
}
