package com.proyecto.proyecto_renta.domain.exceptions;

public class ConflictException extends RuntimeException {
    
    public ConflictException(String message) {
        super(message);
    }
}
