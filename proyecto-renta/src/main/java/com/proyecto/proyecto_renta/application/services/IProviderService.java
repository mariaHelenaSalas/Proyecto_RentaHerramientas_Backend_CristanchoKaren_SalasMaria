package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.Provider;

public interface IProviderService {
    List<Provider> findAll();
    Optional<Provider> findById(Long id);
    Provider save(Provider provider);
    void deleteById(Long id);

}
