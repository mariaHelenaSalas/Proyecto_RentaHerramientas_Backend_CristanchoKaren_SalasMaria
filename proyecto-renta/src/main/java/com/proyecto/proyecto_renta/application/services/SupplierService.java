package com.proyecto.proyecto_renta.application.services;
import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.Supplier;


public interface SupplierService {
    List<Supplier> findAll();
    Optional<Supplier> findById(Long id);
    Supplier save(Supplier supplier);
    void deleteById(Long id);
    boolean existsById(Long id);
    
}
