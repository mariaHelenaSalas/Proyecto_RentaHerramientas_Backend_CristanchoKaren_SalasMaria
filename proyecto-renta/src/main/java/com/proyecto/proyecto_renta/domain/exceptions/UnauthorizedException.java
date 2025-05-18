package com.proyecto.proyecto_renta.domain.exceptions;

public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
}
