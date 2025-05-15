package com.proyecto.proyecto_renta.application.services;

import java.util.List;

import com.proyecto.proyecto_renta.domain.entities.Invoice;

public interface IInvoiceService {
    List<Invoice> findAll();
    Invoice findById(Long id);
    Invoice save(Invoice invoice);
    void deleteById(Long id);
}
