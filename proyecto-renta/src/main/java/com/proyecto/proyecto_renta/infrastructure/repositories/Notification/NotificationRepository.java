package com.proyecto.proyecto_renta.infrastructure.repositories.Notification;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.proyecto_renta.domain.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndReadFalse(Long userId);
}
