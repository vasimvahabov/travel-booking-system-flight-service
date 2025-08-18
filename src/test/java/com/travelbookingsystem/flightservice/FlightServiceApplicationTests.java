package com.travelbookingsystem.flightservice;

import com.travelbookingsystem.flightservice.dto.request.FlightRequest;
import com.travelbookingsystem.flightservice.entity.Flight;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.DATE_TIME_PATTERN;

@ActiveProfiles("integration")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlightServiceApplicationTests {

	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

	@Autowired
    WebTestClient webTestClient;

    @Test
    void whenPostRequestThenFlightIsCreated() {
		var request = FlightRequest.builder()
				.airplaneId(1L)
				.number("AA144")
				.departureAirportCode("DUB")
				.arrivalAirportCode("FRA")
				.departureDateTime(LocalDateTime.parse("2025-07-15 00:12", formatter))
				.arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", formatter))
				.price(BigDecimal.valueOf(15.99)).build();
		webTestClient.post()
		.uri("/api/v1/flights")
				.bodyValue(request)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Flight.class)
				.value(payload -> {
					Assertions.assertThat(payload).isNotNull();
					Assertions.assertThat(payload.getNumber()).isEqualTo(request.getNumber());
				});
    }

}
