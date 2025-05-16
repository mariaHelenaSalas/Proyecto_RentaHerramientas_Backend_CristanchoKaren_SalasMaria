package com.proyecto.proyecto_renta.infrastructure.repository;

import com.proyecto.proyecto_renta.domain.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
}
