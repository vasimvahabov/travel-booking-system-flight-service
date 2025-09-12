package com.travelbookingsystem.flightservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class FlightResponse {

    Long id;

    Long airplaneId;

    String number;

    String departureAirportCode;

    String arrivalAirportCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    LocalDateTime departureDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
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
