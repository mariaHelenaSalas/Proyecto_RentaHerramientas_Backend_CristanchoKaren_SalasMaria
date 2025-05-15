package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.Provider;

public interface IProviderService {
    
    List<Provider> findAll();

    Provider findById(Long id);

    Provider save(Provider provider);

    void deleteById(Long id);

}
