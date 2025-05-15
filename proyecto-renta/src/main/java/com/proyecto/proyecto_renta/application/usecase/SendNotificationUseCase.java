package com.proyecto.proyecto_renta.application.usecase;

import com.proyecto.proyecto_renta.domain.entities.Notification;
import com.proyecto.proyecto_renta.infrastructure.repository.NotificationRepository;

import org.springframework.stereotype.Service;

@Service
public class SendNotificationUseCase {
    private final NotificationRepository notificationRepository;

    public SendNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification sendNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}