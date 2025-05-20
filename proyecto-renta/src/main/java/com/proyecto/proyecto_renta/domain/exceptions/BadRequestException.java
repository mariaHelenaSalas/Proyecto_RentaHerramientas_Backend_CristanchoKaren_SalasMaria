package com.proyecto.proyecto_renta.domain.exceptions;

public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
}
