package com.travelbookingsystem.flightservice.domain;

import java.util.List;
import java.util.Optional;

public interface FlightRepository {

    List<Flight> findAll();

    Optional<Flight> findByNumber(String number);

    boolean existsByNumber(String number);

    Flight save(Flight flight);

    void deleteByNumber(String number);

}
