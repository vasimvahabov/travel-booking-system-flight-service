package com.travelbookingsystem.flightservice.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightRequest {

    Long id;

    @NotNull(message = "The airplane id cannot be null!")
    Long airplaneId;

    @Pattern(
            regexp = "^[A-Z]{2}\\d{1,4}$",
            message = "The flight number is not valid!"
    )
    @NotBlank(message = "The flight number cannot be blank!")
    String number;

    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "The departure airport code number must be 3 uppercase letters (IATA format)!"
    )
    @NotBlank(message = "The departure airport code title cannot be blank!")
    String departureAirportCode;

    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "The arrival airport code must be 3 uppercase letters (IATA format)!"
    )
    @NotBlank(message = "The arrival airport code title cannot be blank!")
    String arrivalAirportCode;

    @NotNull(message = "The departure date time cannot be blank!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    LocalDateTime departureDateTime;

    @NotNull(message = "The arrival date time cannot be blank!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    LocalDateTime arrivalDateTime;

    @NotNull(message = "The flight price cannot be null!")
    @Positive(message = "The flight price cannot be negative!")
    BigDecimal price;

}

