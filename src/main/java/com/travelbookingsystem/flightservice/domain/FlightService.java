package com.travelbookingsystem.flightservice.domain;

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

    public Boolean existsByNumber(String number) {
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
                    f.setAirplaneId(flight.getAirplaneId());
                    f.setDepartureAirportCode(flight.getDepartureAirportCode());
                    f.setArrivalAirportCode(flight.getArrivalAirportCode());
                    f.setDepartureDateTime(flight.getDepartureDateTime());
                    f.setArrivalDateTime(flight.getArrivalDateTime());
                    return flightRepository.save(f);
                }).orElseGet(() -> flightRepository.save(flight))
    }

    public void deleteByNumber(String number) {
        flightRepository.deleteByNumber(number);
    }

}
