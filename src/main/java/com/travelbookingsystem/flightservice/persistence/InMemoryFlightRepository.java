package com.travelbookingsystem.flightservice.persistence;

import com.travelbookingsystem.flightservice.domain.Flight;
import com.travelbookingsystem.flightservice.domain.FlightRepository;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@FieldDefaults(makeFinal = true)
public class InMemoryFlightRepository implements FlightRepository {

    List<Flight> flights = new ArrayList<>();

    @Override
    public List<Flight> findAll() {
        return new ArrayList<>(flights);
    }

    @Override
    public Optional<Flight> findByNumber(String number) {
        return flights.stream().filter(flight -> flight.getNumber().equals(number)).findFirst();
    }

    @Override
    public boolean existsByNumber(String number) {
        return flights.stream().anyMatch(flight -> flight.getNumber().equals(number));
    }

    @Override
    public Flight save(Flight flight) {
        flights.add(flight);
        return flight;
    }

    @Override
    public void deleteByNumber(String number) {
        flights.removeIf(flight -> flight.getNumber().equals(number));
    }
}
