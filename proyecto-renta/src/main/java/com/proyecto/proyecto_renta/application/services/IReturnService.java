package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.Return;

public interface IReturnService {
    List<Return> findAll();
    Optional<Return> findById(Long id);
    Return save(Return returnEntity);
    void deleteById(Long id);
    List<Return> findByProviderUserId(Long userId);
    List<Return> findByClientUserId(Long userId);
}
