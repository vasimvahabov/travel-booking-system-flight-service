package com.travelbookingsystem.flightservice.config;

import com.travelbookingsystem.flightservice.domain.Flight;
import com.travelbookingsystem.flightservice.domain.FlightRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.DATE_TIME_PATTERN;

@Component
//@Profile("test-data")
@ConditionalOnProperty(name = "spring.application.test-data-enabled", havingValue = "true")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestDataLoader {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    FlightRepository flightRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        var flight1 = Flight.builder()
                .airplaneId(1L)
                .number("AA144")
                .departureAirportCode("DUB")
                .arrivalAirportCode("FRA")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();

        var flight2 = Flight.builder()
                .airplaneId(2L)
                .number("AA133")
                .departureAirportCode("FRA")
                .arrivalAirportCode("DUB")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();

        flightRepository.save(flight1);
        flightRepository.save(flight2);


    }

}
