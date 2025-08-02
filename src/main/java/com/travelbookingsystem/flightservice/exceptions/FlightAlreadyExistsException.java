package com.travelbookingsystem.flightservice.exceptions;

public class FlightAlreadyExistsException extends RuntimeException {

    public FlightAlreadyExistsException(String flightNumber) {
        super("A flight with %s number already exists!!!".formatted(flightNumber));
    }

}
