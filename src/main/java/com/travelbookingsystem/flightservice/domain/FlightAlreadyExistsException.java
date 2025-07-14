package com.travelbookingsystem.flightservice.domain;

public class FlightAlreadyExistsException extends RuntimeException {

    public FlightAlreadyExistsException(String flightNumber) {
        super("A flight with %s number already exists!!!".formatted(flightNumber));
    }

}
