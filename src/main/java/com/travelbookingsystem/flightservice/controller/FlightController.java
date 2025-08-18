package com.travelbookingsystem.flightservice.controller;

import com.travelbookingsystem.flightservice.dto.request.FlightRequest;
import com.travelbookingsystem.flightservice.dto.response.FlightResponse;
import com.travelbookingsystem.flightservice.service.FlightService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flights")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightController {

    FlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightResponse>> findAll() {
        return ResponseEntity.ok(flightService.findAll());
    }

    @GetMapping("{number}")
    public ResponseEntity<FlightResponse> findByNumber(@PathVariable String number) {
        return ResponseEntity.ok(flightService.findByNumber(number));
    }

    @PostMapping
    public ResponseEntity<FlightResponse> create(@RequestBody @Valid FlightRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.create(request));
    }

    @PutMapping
    public ResponseEntity<FlightResponse> update(@RequestBody @Valid FlightRequest request) {
        return ResponseEntity.ok(flightService.update(request));
    }

    @DeleteMapping("{number}")
    public ResponseEntity<Void> deleteByNumber(@PathVariable String number) {
        flightService.deleteByNumber(number);
        return ResponseEntity.noContent().build();
    }

}