package com.proyecto.proyecto_renta.infrastructure.repository;

import com.proyecto.proyecto_renta.domain.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdUser(Long userId);
    
    List<Notification> findByUserIdUserAndReadFalse(Long userId);
    
    @Modifying
    @Query("UPDATE Notification n SET n.read = true WHERE n.user.idUser = :userId AND n.read = false")
    void markAllAsReadByUserId(@Param("userId") Long userId);
}
