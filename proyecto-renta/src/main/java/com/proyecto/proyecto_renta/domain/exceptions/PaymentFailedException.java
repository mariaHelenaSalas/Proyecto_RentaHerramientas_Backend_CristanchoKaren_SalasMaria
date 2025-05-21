package com.proyecto.proyecto_renta.domain.exceptions;


public class PaymentFailedException extends RuntimeException {
    public PaymentFailedException(String message) {
        super(message);
    }
}
