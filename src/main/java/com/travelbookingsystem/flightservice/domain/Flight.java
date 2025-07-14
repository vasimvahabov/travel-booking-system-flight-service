package com.travelbookingsystem.flightservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Flight {

    Long airplaneId;

    @Pattern(
            regexp = "^[A-Z]{3}$\n",
            message = "The flight number is not valid!"
    )
    @NotBlank(message = "The flight number cannot be blank!")
    String number;

    @Pattern(
            regexp = "^[A-Z]{2}\\d{1,4}$\n",
            message = "The departure airport code number is not valid!"
    )
    @NotBlank(message = "The departure airport code title cannot be blank!")
    String departureAirportCode;

    @Pattern(
            regexp = "^[A-Z]{2}\\d{1,4}$\n",
            message = "The arrival airport code number is not valid!"
    )
    @NotBlank(message = "The arrival airport code title cannot be blank!")
    String arrivalAirportCode;

    @NotBlank(message = "The arrival date time cannot be blank!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime arrivalDateTime;

    @NotBlank(message = "The departure date time cannot be blank!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime departureDateTime;

}
