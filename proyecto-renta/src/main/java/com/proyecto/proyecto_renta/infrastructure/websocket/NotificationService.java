package com.proyecto.proyecto_renta.infrastructure.websocket;

import com.proyecto.proyecto_renta.domain.dto.NotificationMessage;
import com.proyecto.proyecto_renta.domain.entities.Notification;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.infrastructure.repository.NotificationRepository;
import com.proyecto.proyecto_renta.infrastructure.repository.UserRepository;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(SimpMessagingTemplate messagingTemplate, 
                              NotificationRepository notificationRepository,
                              UserRepository userRepository) {
        this.messagingTemplate = messagingTemplate;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void sendNotification(Long userId, String message, Notification.Type type) {
        // Crear mensaje de notificación
        NotificationMessage notificationMessage = new NotificationMessage(message, type.name(), userId);
        
        // Enviar a tema WebSocket
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notificationMessage);
        
        // Guardar en base de datos
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRead(false);
        
        notificationRepository.save(notification);
    }
    
    public void sendReservationNotification(Long providerId, Long clientId, String toolName) {
        // Notificar al proveedor
        sendNotification(providerId, 
                "Your tool \"" + toolName + "\" has been reserved", 
                Notification.Type.RESERVATION);
        
        // Notificar al cliente
        sendNotification(clientId, 
                "Your reservation for \"" + toolName + "\" has been created", 
                Notification.Type.RESERVATION);
    }
    
    public void sendPaymentNotification(Long providerId, Long clientId, String toolName) {
        // Notificar al proveedor
        sendNotification(providerId, 
                "Payment received for tool \"" + toolName + "\"", 
                Notification.Type.PAYMENT);
        
        // Notificar al cliente
        sendNotification(clientId, 
                "Your payment for \"" + toolName + "\" has been processed", 
                Notification.Type.PAYMENT);
    }
    
    public void sendReturnNotification(Long providerId, Long clientId, String toolName) {
        // Notificar al proveedor
        sendNotification(providerId, 
                "Tool \"" + toolName + "\" has been returned", 
                Notification.Type.REMINDER);
        
        // Notificar al cliente
        sendNotification(clientId, 
                "Return of \"" + toolName + "\" has been processed", 
                Notification.Type.REMINDER);
    }
}
