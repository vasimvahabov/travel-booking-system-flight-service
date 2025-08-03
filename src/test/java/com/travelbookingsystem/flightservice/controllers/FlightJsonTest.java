package com.travelbookingsystem.flightservice.controllers;

import com.travelbookingsystem.flightservice.dtos.request.FlightRequest;
import com.travelbookingsystem.flightservice.dtos.response.FlightResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.DATE_TIME_PATTERN;

@JsonTest
class FlightJsonTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Autowired
    private JacksonTester<FlightRequest> jacksonTester;

    @Test
    void testSerialization() throws IOException {

        var request = FlightRequest.builder()
                .airplaneId(1L)
                .number("AA144")
                .departureAirportCode("DUB")
                .arrivalAirportCode("FRA")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();
        var jsonContent = jacksonTester.write(request);

        Assertions.assertThat(jsonContent).satisfies(json -> {

            var actualAirplaneId = Assertions.assertThat(jsonContent)
                    .extractingJsonPathNumberValue("@.airplaneId").actual();
            Assertions.assertThat(actualAirplaneId).isEqualTo(request.getAirplaneId());


            Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.number")
                    .isEqualTo(request.getNumber());

            Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.departureAirportCode")
                    .isEqualTo(request.getDepartureAirportCode());

            Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.arrivalAirportCode")
                    .isEqualTo(request.getArrivalAirportCode());


            var actualDepartureDateTime = Assertions.assertThat(jsonContent)
                    .extractingJsonPathStringValue("@.departureDateTime").actual();
            Assertions.assertThat(LocalDateTime.parse(actualDepartureDateTime, formatter))
                    .isEqualTo(request.getDepartureDateTime());

            var actualArrivalDateTime = Assertions.assertThat(jsonContent)
                    .extractingJsonPathStringValue("@.arrivalDateTime").actual();
            Assertions.assertThat(LocalDateTime.parse(actualArrivalDateTime, formatter))
                    .isEqualTo(request.getArrivalDateTime());
        });

    }

    @Test
    void testDeserialization() throws IOException {
        var response = FlightResponse.builder()
                .airplaneId(1L)
                .number("AA144")
                .departureAirportCode("DUB")
                .arrivalAirportCode("FRA")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();

        var content = """
                {
                  "airplaneId": 1,
                  "number": "AA144",
                  "departureAirportCode": "DUB",
                  "arrivalAirportCode": "FRA",
                  "departureDateTime": "2025-07-15 00:12",
                  "arrivalDateTime": "2025-07-15 05:00",
                  "price": 15.99
                }
        """;


        Assertions.assertThat(jacksonTester.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(response);
    }


}
