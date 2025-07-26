package com.travelbookingsystem.flightservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.DATE_TIME_PATTERN;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);


    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    @Test
    void whenFlightAlreadyExistsThenThrowsFlightAlreadyExistsException() {
        var flight = Flight.builder()
                .airplaneId(1L)
                .number("AA144")
                .departureAirportCode("DUB")
                .arrivalAirportCode("FRA")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();

        Mockito.when(flightRepository.existsByNumber(flight.getNumber())).thenReturn(true);
        Assertions.assertThatThrownBy(() -> flightService.create(flight))
                .isInstanceOf(FlightAlreadyExistsException.class)
                .hasMessage("A flight with %s number already exists!!!".formatted(flight.getNumber()));
    }

    @Test
    void whenFlightNotFoundThenThrowsFlightNotFoundException(){
        var flightNumber = "AA144";
        Mockito.when(flightRepository.findByNumber(flightNumber)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()-> flightService.findByNumber(flightNumber))
                .isInstanceOf(FlightNotFoundException.class)
                .hasMessage("A flight with %s number not found!!!".formatted(flightNumber));
    }

}
