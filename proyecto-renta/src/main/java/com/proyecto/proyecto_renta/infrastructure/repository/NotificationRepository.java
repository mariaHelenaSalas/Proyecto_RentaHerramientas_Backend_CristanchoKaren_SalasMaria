package com.proyecto.proyecto_renta.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.proyecto.proyecto_renta.domain.entities.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long>{
    List<Notification> findByUserIdAndReadIsFalse(Long userId);
}
