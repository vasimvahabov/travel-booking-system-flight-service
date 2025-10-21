package com.travelbookingsystem.flightservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.travelbookingsystem.flightservice.config.KeycloakProperties;
import com.travelbookingsystem.flightservice.dto.request.FlightRequest;
import com.travelbookingsystem.flightservice.dto.response.FlightResponse;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.DATE_TIME_PATTERN;

@Testcontainers
@ActiveProfiles("integration")
@EnableConfigurationProperties(KeycloakProperties.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlightServiceApplicationTests {

	final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    AccessToken tokenEmployee;

    AccessToken tokenCustomer;

    @Autowired
    KeycloakProperties keycloakProperties;

	@Autowired
    WebTestClient webTestClient;

    @Container
    static final KeycloakContainer KEYCLOAK_CONTAINER
            = new KeycloakContainer("quay.io/keycloak/keycloak:26.3")
            .withRealmImportFile("keycloak/travel-booking-system-realm.json")
            .withReuse(Boolean.TRUE);

    static {
        KEYCLOAK_CONTAINER.start();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> KEYCLOAK_CONTAINER.getAuthServerUrl()+ "/realms/travel-booking-system");
    }

    @BeforeAll
    void generateAccessTokens() {
        var webClient = WebClient.builder()
                .baseUrl(
                        KEYCLOAK_CONTAINER.getAuthServerUrl()
                                + "/realms/travel-booking-system/protocol/openid-connect/token"
                ).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        var employee = keycloakProperties.findUserByUsername("edge-server-employee");
        tokenEmployee = authenticate(employee.getUsername(), employee.getPassword(), webClient);

        var customer = keycloakProperties.findUserByUsername("edge-server-customer");
        tokenCustomer = authenticate(customer.getUsername(), customer.getPassword(), webClient);

    }

    private AccessToken authenticate(String username, String password, WebClient webClient) {
        return webClient.post()
                .body(
                        BodyInserters
                                .fromFormData("grant_type","password")
                                .with("client_id", keycloakProperties.getClient().getId())
                                .with("client_secret", keycloakProperties.getClient().getSecret())
                                .with("username", username)
                                .with("password", password)

                ).retrieve()
                .bodyToMono(AccessToken.class)
                .block();
    }


    @Test
    void whenPostRequestThenFlightIsCreated() {
		var request = FlightRequest.builder()
				.airplaneId(1L)
				.number("AA144")
				.departureAirportCode("DUB")
				.arrivalAirportCode("FRA")
				.departureDateTime(LocalDateTime.parse("2025-07-15 00:12", FORMATTER))
				.arrivalDateTime(LocalDateTime.parse("2025-07-15 05:00", FORMATTER))
				.price(BigDecimal.valueOf(15.99)).build();

		webTestClient.post()
		        .uri("/api/v1/flights")
                .headers(headers -> headers.setBearerAuth(tokenEmployee.accessToken))
				.bodyValue(request)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(FlightResponse.class)
				.value(payload -> {
					Assertions.assertThat(payload).isNotNull();
					Assertions.assertThat(payload.getNumber()).isEqualTo(request.getNumber());
				});
    }

    private record AccessToken(String accessToken) {

        @JsonCreator
        private AccessToken(@JsonProperty("access_token") final String accessToken) {
            this.accessToken = accessToken;
        }

    }

}
