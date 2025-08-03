package com.travelbookingsystem.flightservice.services;

import com.travelbookingsystem.flightservice.entities.Flight;
import com.travelbookingsystem.flightservice.exceptions.FlightAlreadyExistsException;
import com.travelbookingsystem.flightservice.exceptions.FlightNotFoundException;
import com.travelbookingsystem.flightservice.repository.FlightRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightService {

    FlightRepository flightRepository;

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public Flight findByNumber(String number) {
        return flightRepository.findByNumber(number)
                .orElseThrow(() -> new FlightNotFoundException(number));
    }

    public boolean existsByNumber(String number) {
        return flightRepository.existsByNumber(number);
    }

    public Flight create(Flight flight) {
        if(existsByNumber(flight.getNumber())) {
            throw new FlightAlreadyExistsException(flight.getNumber());
        }
        return flightRepository.save(flight);
    }

    public Flight update(Flight flight) {
        return flightRepository.findByNumber(flight.getNumber())
                .map(f -> {
                    f.setId(flight.getId());
                    f.setVersion(flight.getVersion());
                    f.setAirplaneId(flight.getAirplaneId());
                    f.setDepartureAirportCode(flight.getDepartureAirportCode());
                    f.setArrivalAirportCode(flight.getArrivalAirportCode());
                    f.setDepartureDateTime(flight.getDepartureDateTime());
                    f.setArrivalDateTime(flight.getArrivalDateTime());
                    f.setPrice(flight.getPrice());
                    return flightRepository.save(f);
                }).orElseGet(() -> flightRepository.save(flight));
    }

    public void deleteByNumber(String number) {
        flightRepository.deleteByNumber(number);
    }

}
