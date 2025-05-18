package com.proyecto.proyecto_renta.infrastructure.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.proyecto.proyecto_renta.domain.exceptions.BadRequestException;
import com.proyecto.proyecto_renta.domain.exceptions.BusinessLogicException;
import com.proyecto.proyecto_renta.domain.exceptions.ConflictException;
import com.proyecto.proyecto_renta.domain.exceptions.ForbiddenException;
import com.proyecto.proyecto_renta.domain.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto_renta.domain.exceptions.UnauthorizedException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    // Gestión de excepciones de validación
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                "Fallo en la validación de la entrada",
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> 
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage())
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    // Gestión de las violaciones de las restricciones
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                errors.toString(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    // Manejar la excepción de recurso no encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "No se ha encontrado",
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    // Excepción de solicitud incorrecta
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(
            BadRequestException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Mala petición",
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    // Manejar excepción no autorizada
    @ExceptionHandler({UnauthorizedException.class, AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<Object> handleUnauthorizedException(
            Exception ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Sin autorización",
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    
    // Manejar excepción prohibida
    @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
    public ResponseEntity<Object> handleForbiddenException(
            Exception ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Prohibido",
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    
    // Manejar la excepción de conflicto
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(
            ConflictException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflicto",
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    
    // Manejar excepciones de lógica de negocio
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<Object> handleBusinessLogicException(
            BusinessLogicException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Error de lógica empresarial",
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    // Manejar todas las demás excepciones
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtException(
            Exception ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred",
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
