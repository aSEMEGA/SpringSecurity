package com.example.TrainingSprintBoot.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class PersonnalizeException extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ExceptionNotfound.class})
    public ResponseEntity<Object> handleNotfoundRessource(ExceptionNotfound exceptionNotfound){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exceptionNotfound.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
    @ExceptionHandler({TokenExpiredException.class})
    public ResponseEntity<Object> handleTokenExpired(TokenExpiredException tokenExpiredException){
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, tokenExpiredException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
}
