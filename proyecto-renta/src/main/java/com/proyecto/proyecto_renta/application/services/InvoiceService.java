package com.proyecto.proyecto_renta.application.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto_renta.domain.entities.Invoice;



public interface InvoiceService {
    List<Invoice> findAll();
    Optional<Invoice> findById(Long id);
    Invoice save(Invoice invoice);
    void deleteById(Long id);
    boolean existsById(Long id);
    
}
