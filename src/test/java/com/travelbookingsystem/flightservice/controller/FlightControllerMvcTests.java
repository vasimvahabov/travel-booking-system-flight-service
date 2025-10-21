package com.travelbookingsystem.flightservice.controller;

import com.travelbookingsystem.flightservice.config.SecurityConfig;
import com.travelbookingsystem.flightservice.exception.FlightNotFoundException;
import com.travelbookingsystem.flightservice.service.FlightService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.*;

@WebMvcTest(FlightController.class)
@Import(SecurityConfig.class)
class FlightControllerMvcTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    FlightService flightService;

    @MockitoBean
    JwtDecoder jwtDecoder;

    @Test
    void givenFlightDoesNotExist_whenFindFlightById_thenReturn404() throws Exception {
        final var flightNumber = "AA144";

        BDDMockito.willThrow(FlightNotFoundException.class)
                .given(flightService).findByNumber(flightNumber);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/flights/%s".formatted(flightNumber))
                                .with(SecurityMockMvcRequestPostProcessors.jwt())
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        BDDMockito.then(flightService).should().findByNumber(flightNumber);
    }

    @Test
    void givenRoleEmployee_whenDeleteFlightByNumber_thenReturn204() throws Exception {
        final var flightNumber = "AA144";

        BDDMockito.willDoNothing().given(flightService).deleteByNumber(flightNumber);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/flights/%s".formatted(flightNumber))
                        .with(SecurityMockMvcRequestPostProcessors
                                .jwt()
                                .authorities(new SimpleGrantedAuthority(AUTHORITY_EMPLOYEE)))
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

        BDDMockito.then(flightService).should().deleteByNumber(flightNumber);
    }

    @Test
    void givenRoleCustomer_whenDeleteFlightByNumber_thenReturn403() throws Exception {
        final var flightNumber = "AA144";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/flights/%s".formatted(flightNumber))
                        .with(SecurityMockMvcRequestPostProcessors
                                .jwt()
                                .authorities(new SimpleGrantedAuthority(AUTHORITY_CUSTOMER)))
        ).andExpect(MockMvcResultMatchers.status().isForbidden());

        BDDMockito.then(flightService).shouldHaveNoInteractions();
    }

    @Test
    void givenNotAuthenticated_whenDeleteFlightByNumber_thenReturn401() throws Exception {
        final var flightNumber = "AA144";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/flights/%s".formatted(flightNumber))
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized());

        BDDMockito.then(flightService).shouldHaveNoInteractions();

    }

}
