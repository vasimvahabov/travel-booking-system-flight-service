package com.travelbookingsystem.flightservice.repository;

import com.travelbookingsystem.flightservice.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByNumber(String number);

    boolean existsByNumber(String number);

    void deleteByNumber(String number);

}
