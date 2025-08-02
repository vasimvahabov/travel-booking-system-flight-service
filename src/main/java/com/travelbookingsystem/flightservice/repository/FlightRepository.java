package com.travelbookingsystem.flightservice.repository;

import com.travelbookingsystem.flightservice.entities.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightRepository {

    List<Flight> findAll();

    Optional<Flight> findByNumber(String number);

    boolean existsByNumber(String number);

    Flight save(Flight flight);

    void deleteByNumber(String number);

}
