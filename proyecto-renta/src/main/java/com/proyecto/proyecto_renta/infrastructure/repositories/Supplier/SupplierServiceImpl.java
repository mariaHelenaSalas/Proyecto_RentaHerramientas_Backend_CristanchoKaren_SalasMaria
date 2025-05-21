package com.proyecto.proyecto_renta.infrastructure.repositories.Supplier;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.proyecto.proyecto_renta.domain.entities.Supplier;
import com.proyecto.proyecto_renta.application.services.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return supplierRepository.existsById(id);
    }
}
