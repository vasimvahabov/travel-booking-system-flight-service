package com.travelbookingsystem.flightservice;

import com.travelbookingsystem.flightservice.domain.Flight;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.DATE_TIME_PATTERN;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class FlightServiceApplicationTests {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

	@Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenFlightIsCreated() {
		Flight flight = Flight.builder()
				.airplaneId(1L)
				.number("AA144")
				.departureAirportCode("DUB")
				.arrivalAirportCode("FRA")
				.departureDateTime(LocalDateTime.parse("2025-07-15 00:12", FORMATTER))
				.arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", FORMATTER))
				.price(BigDecimal.valueOf(15.99)).build();
		webTestClient.post()
		.uri("/flights")
				.bodyValue(flight)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Flight.class)
				.value(payload -> {
					Assertions.assertThat(payload).isNotNull();
					Assertions.assertThat(payload.getNumber()).isEqualTo(flight.getNumber());
				});
    }

}
