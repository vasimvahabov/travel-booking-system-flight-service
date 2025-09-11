package com.travelbookingsystem.flightservice.dto.response;

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
public class FlightResponse {

    Long id;

    Long airplaneId;

    String number;

    String departureAirportCode;

    String arrivalAirportCode;

    LocalDateTime departureDateTime;

    LocalDateTime arrivalDateTime;

    BigDecimal price;

    @Override
    public String toString() {
        return "FlightResponse{" +
                "id=" + id +
                ", airplaneId=" + airplaneId +
                ", number='" + number + '\'' +
                ", departureAirportCode='" + departureAirportCode + '\'' +
                ", arrivalAirportCode='" + arrivalAirportCode + '\'' +
                ", departureDateTime=" + departureDateTime +
                ", arrivalDateTime=" + arrivalDateTime +
                ", price=" + price +
                '}';
    }

}
