package com.travelbookingsystem.flightservice.web;

import com.travelbookingsystem.flightservice.domain.FlightNotFoundException;
import com.travelbookingsystem.flightservice.domain.FlightService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(FlightController.class)
class FlightControllerMvcTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    FlightService flightService;

    @Test
    void whenFlightNotExistsThenReturnNotFound() throws Exception {
        final var flightNumber = "AA144";
        BDDMockito.given(flightService.findByNumber(flightNumber)).willThrow(FlightNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/flights/%s".formatted(flightNumber)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
