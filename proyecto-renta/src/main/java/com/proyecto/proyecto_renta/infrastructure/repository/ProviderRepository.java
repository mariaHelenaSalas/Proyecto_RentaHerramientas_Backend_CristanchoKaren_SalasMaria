package com.proyecto.proyecto_renta.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.proyecto_renta.domain.entities.Provider;

public interface ProviderRepository extends CrudRepository<Provider, Long> {
  

}
