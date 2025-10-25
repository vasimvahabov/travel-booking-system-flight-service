package com.travelbookingsystem.flightservice.repository;

import com.travelbookingsystem.flightservice.config.AuditConfig;
import com.travelbookingsystem.flightservice.entity.Flight;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithMockUser;
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
    final String USER_ID = "182a5a6e-918b-4110-ba0c-8300f6e90662";

    @Autowired
    FlightRepository flightRepository;

    @Test
    @WithMockUser(USER_ID)
    void whenUnauthenticated_thenThrowsDataIntegrityViolationException() {
        var flight = Flight.builder()
                .airplaneId(5L)
                .number("BC544")
                .departureAirportCode("PLN")
                .arrivalAirportCode("GYD")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();
        flightRepository.save(flight);
        var optionalFlight = flightRepository.findByNumber(flight.getNumber());

        Assertions.assertThat(optionalFlight).isPresent();
        Assertions.assertThat(optionalFlight.get().getNumber()).isEqualTo(flight.getNumber());

    }

    @Test
    void whenAuthenticated_thenSavesFlight() {
        var flight = Flight.builder()
                .airplaneId(5L)
                .number("BC544")
                .departureAirportCode("PLN")
                .arrivalAirportCode("GYD")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();
        Assertions.assertThatThrownBy(() ->
                flightRepository.saveAndFlush(flight)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

}
