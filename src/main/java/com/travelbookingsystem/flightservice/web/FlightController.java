package com.travelbookingsystem.flightservice.web;

import com.travelbookingsystem.flightservice.domain.Flight;
import com.travelbookingsystem.flightservice.domain.FlightService;
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
    public ResponseEntity<List<Flight>> findAll() {
        return ResponseEntity.ok(flightService.findAll());
    }

    @GetMapping("{number}")
    public ResponseEntity<Flight> findByNumber(@PathVariable String number) {
        return ResponseEntity.ok(flightService.findByNumber(number));
    }

    @PostMapping
    public ResponseEntity<Flight> create(@RequestBody @Valid Flight flight) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.create(flight));
    }

    @PutMapping
    public ResponseEntity<Flight> update(@RequestBody @Valid Flight flight) {
        return ResponseEntity.ok(flightService.update(flight));
    }

    @DeleteMapping("{number}")
    public ResponseEntity<Flight> deleteByNumber(@PathVariable String number) {
        flightService.deleteByNumber(number);
        return ResponseEntity.noContent().build();
    }

}