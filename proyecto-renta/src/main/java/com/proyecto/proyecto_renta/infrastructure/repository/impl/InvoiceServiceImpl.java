package com.proyecto.proyecto_renta.infrastructure.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.proyecto_renta.application.services.IInvoiceService;
import com.proyecto.proyecto_renta.domain.entities.Invoice;
import com.proyecto.proyecto_renta.infrastructure.repository.InvoiceRepository;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

    private final InvoiceRepository repository;

    public InvoiceServiceImpl(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Invoice> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return repository.save(invoice);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}