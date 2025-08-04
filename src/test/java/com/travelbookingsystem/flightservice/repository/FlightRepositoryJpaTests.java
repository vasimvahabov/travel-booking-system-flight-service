package com.travelbookingsystem.flightservice.repository;

import com.travelbookingsystem.flightservice.config.AuditConfig;
import com.travelbookingsystem.flightservice.entities.Flight;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.*;

@DataJpaTest
@Import(AuditConfig.class)
@ActiveProfiles("integration")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FlightRepositoryJpaTests {

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Autowired
    FlightRepository flightRepository;

    @Test
    void findFlightByIdAfterInsert() {
        var flight = Flight.builder()
                .airplaneId(1L)
                .number("AA144")
                .departureAirportCode("DUB")
                .arrivalAirportCode("FRA")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();
        flightRepository.save(flight);
        var optionalFlight = flightRepository.findByNumber(flight.getNumber());

        Assertions.assertThat(optionalFlight).isPresent();
        Assertions.assertThat(optionalFlight.get().getNumber()).isEqualTo(flight.getNumber());

    }

}
