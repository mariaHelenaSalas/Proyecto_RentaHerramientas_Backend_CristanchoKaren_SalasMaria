package com.proyecto.proyecto_renta.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.proyecto_renta.domain.entities.History;

public interface HistoryRepository extends CrudRepository<History, Long> {
    List<History> findByUserId(Long userId);
}
