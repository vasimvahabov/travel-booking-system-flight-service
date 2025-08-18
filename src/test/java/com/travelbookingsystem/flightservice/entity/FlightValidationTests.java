package com.travelbookingsystem.flightservice.entity;

import com.travelbookingsystem.flightservice.dto.request.FlightRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class FlightValidationTests {

    static Validator validator;
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void whenAllFieldsValidThenValidationSucceeds() {
        var request = FlightRequest.builder()
                .airplaneId(1L)
                .number("AA144")
                .departureAirportCode("DUB")
                .arrivalAirportCode("FRA")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();
        Set<ConstraintViolation<FlightRequest>> violations = validator.validate(request);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void whenDepartureDateTimeNullThenValidationFails() {
        var request = FlightRequest.builder()
                .airplaneId(1L)
                .number("AA144")
                .departureAirportCode("DUB")
                .arrivalAirportCode("FRA")
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();
        Set<ConstraintViolation<FlightRequest>> violations = validator.validate(request);
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat("The departure date time cannot be blank!".equals(violations.iterator().next().getMessage())).isEqualTo(Boolean.TRUE);
    }

}
