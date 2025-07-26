package com.travelbookingsystem.flightservice.web;

import com.travelbookingsystem.flightservice.domain.Flight;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.DATE_TIME_PATTERN;

@JsonTest
class FlightJsonTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Autowired
    private JacksonTester<Flight> jacksonTester;

    @Test
    void testSerialization() throws IOException {
        var flight = Flight.builder()
                .airplaneId(1L)
                .number("AA144")
                .departureAirportCode("DUB")
                .arrivalAirportCode("FRA")
                .departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
                .arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
                .price(BigDecimal.valueOf(15.99)).build();
        var jsonContent = jacksonTester.write(flight);

        Assertions.assertThat(
                Assertions.assertThat(jsonContent)
                        .extractingJsonPathNumberValue("@.airplaneId")
                        .actual().longValue()
        ).isEqualTo(flight.getAirplaneId());

        Assertions.assertThat(jsonContent)
                .extractingJsonPathStringValue("@.number")
                .isEqualTo(flight.getNumber());

        Assertions.assertThat(jsonContent)
                .extractingJsonPathStringValue("@.departureAirportCode")
                .isEqualTo(flight.getDepartureAirportCode());

        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.arrivalAirportCode")
                .isEqualTo(flight.getArrivalAirportCode());

        Assertions.assertThat(
                LocalDateTime.parse(
                        Assertions.assertThat(jsonContent)
                                .extractingJsonPathStringValue("@.departureDateTime").actual(), formatter
                )
        ).isEqualTo(flight.getDepartureDateTime());

        Assertions.assertThat(
                LocalDateTime.parse(
                        Assertions.assertThat(jsonContent)
                                .extractingJsonPathStringValue("@.arrivalDateTime").actual(), formatter
                )
        ).isEqualTo(flight.getArrivalDateTime());

    }

    @Test
    void testDeserialization() throws IOException {
        var flight = Flight.builder()
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
                .isEqualTo(flight);
    }


}
