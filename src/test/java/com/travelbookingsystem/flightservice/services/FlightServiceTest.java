package com.travelbookingsystem.flightservice.services;

import com.travelbookingsystem.flightservice.dtos.request.FlightRequest;
import com.travelbookingsystem.flightservice.exceptions.FlightAlreadyExistsException;
import com.travelbookingsystem.flightservice.exceptions.FlightNotFoundException;
import com.travelbookingsystem.flightservice.mapper.FlightMapper;
import com.travelbookingsystem.flightservice.repository.FlightRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
class FlightServiceTest {

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Mock
    FlightRepository flightRepository;

    @Mock
    FlightMapper flightMapper;

    @InjectMocks
    FlightService flightService;

    @Test
    void whenFlightAlreadyExistsThenThrowsFlightAlreadyExistsException() {
        var request = FlightRequest.builder()
                .airplaneId(1L)
                .number("AA144")
                .departureAirportCode("DUB")
                .arrivalAirportCode("FRA")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();

        Mockito.when(flightRepository.existsByNumber(request.getNumber())).thenReturn(true);
        Assertions.assertThatThrownBy(() -> flightService.create(request))
                .isInstanceOf(FlightAlreadyExistsException.class)
                .hasMessage("A flight with %s number already exists!!!".formatted(request.getNumber()));
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
