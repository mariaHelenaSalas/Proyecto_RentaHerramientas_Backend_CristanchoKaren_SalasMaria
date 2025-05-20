package com.proyecto.proyecto_renta.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {
    private String message;
    private String type;
    private Long userId;
    private LocalDateTime timestamp = LocalDateTime.now();
    
    public NotificationMessage(String message, String type, Long userId) {
        this.message = message;
        this.type = type;
        this.userId = userId;
    }
}
