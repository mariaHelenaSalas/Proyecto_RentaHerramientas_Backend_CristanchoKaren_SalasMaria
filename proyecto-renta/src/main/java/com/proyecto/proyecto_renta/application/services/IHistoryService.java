package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.History;

public interface IHistoryService {

    List<History> findAll();

    History findById(Long id); 

    History save(History history);
    
    void deleteById(Long id);

}
