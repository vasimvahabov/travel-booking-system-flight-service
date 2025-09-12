package com.travelbookingsystem.flightservice.service;

import com.travelbookingsystem.flightservice.dto.request.FlightRequest;
import com.travelbookingsystem.flightservice.dto.response.FlightResponse;
import com.travelbookingsystem.flightservice.exception.FlightAlreadyExistsException;
import com.travelbookingsystem.flightservice.exception.FlightNotFoundException;
import com.travelbookingsystem.flightservice.mapper.FlightMapper;
import com.travelbookingsystem.flightservice.repository.FlightRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightService {

    FlightRepository flightRepository;
    FlightMapper flightMapper;

    public List<FlightResponse> findAll() {
        var responses = flightRepository
                .findAll()
                .stream()
                .map(flightMapper::entityToResponse)
                .toList();

        log.info("Successfully retrieved flights : {}", responses.size());
        return responses;
    }

    public FlightResponse findByNumber(String number) {
        var response = flightRepository.findByNumber(number)
                .map(flightMapper::entityToResponse)
                .orElseThrow(() -> new FlightNotFoundException(number));

        log.info("Successfully retrieved flight: {}", response);
        return response;
    }

    public FlightResponse create(FlightRequest request) {
        if (existsByNumber(request.getNumber())) {
            throw new FlightAlreadyExistsException(request.getNumber());
        }
        var response = flightMapper.entityToResponse(
                flightRepository.save(flightMapper.requestToEntity(request))
        );

        log.info("Successfully created flight: {}", response);
        return response;
    }

    public FlightResponse update(FlightRequest request) {
        var response = flightRepository.findByNumber(request.getNumber())
                .map(flight -> flightMapper.updateEntityFromRequest(request, flight))
                .map(flightMapper::entityToResponse)
                .orElseGet(() -> flightMapper.entityToResponse(
                        flightRepository.save(flightMapper.requestToEntity(request))
                ));

        log.info("Successfully updated flight: : {}", response);
        return response;
    }

    public void deleteByNumber(String number) {
        boolean isExist = existsByNumber(number);
        if (isExist) {
            flightRepository.deleteByNumber(number);
        }

        log.info(isExist
                ? "Flight with number {} deleted"
                : "Flight with number {} not found, nothing deleted", number);
    }

    private boolean existsByNumber(String number) {
        return flightRepository.existsByNumber(number);
    }

}
