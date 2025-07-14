package com.travelbookingsystem.flightservice.domain;

public class FlightNotFoundException extends RuntimeException {

    public FlightNotFoundException(String flightNumber) {
        super("A flight with %s number not found!!!".formatted(flightNumber));
    }

}
