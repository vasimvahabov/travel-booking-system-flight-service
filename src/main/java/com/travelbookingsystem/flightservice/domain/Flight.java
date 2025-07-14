package com.travelbookingsystem.flightservice.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Flight {

    Long airplaneId;

    String number;

    String departureAirportCode;

    String arrivalAirportCode;

    LocalDateTime arrivalDateTime;

    LocalDateTime departureDateTime;

}
