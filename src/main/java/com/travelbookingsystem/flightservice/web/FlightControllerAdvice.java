package com.travelbookingsystem.flightservice.web;

import com.travelbookingsystem.flightservice.domain.CustomExceptionMessage;
import com.travelbookingsystem.flightservice.domain.FlightAlreadyExistsException;
import com.travelbookingsystem.flightservice.domain.FlightNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
public class FlightControllerAdvice {

    @ExceptionHandler(FlightAlreadyExistsException.class)
    public ResponseEntity<CustomExceptionMessage> flightAlreadyExistsHandler(FlightAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(CustomExceptionMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<CustomExceptionMessage> flightNotFoundHandler(FlightNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CustomExceptionMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<CustomExceptionMessage>> notValidHandler(MethodArgumentNotValidException exception) {
        var exceptionMessages = exception.getBindingResult().getAllErrors().stream()
                .map(error -> CustomExceptionMessage.builder().message(error.getDefaultMessage()).build()).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessages);
    }

}
