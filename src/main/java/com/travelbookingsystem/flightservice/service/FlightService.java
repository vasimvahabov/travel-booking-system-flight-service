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
        var flightResponses = flightRepository
                .findAll()
                .stream()
                .map(flightMapper::entityToResponse)
                .toList();

        log.info("{} flights returned", flightResponses.size());
        return flightResponses;
    }

    public FlightResponse findByNumber(String number) {
        var response = flightRepository.findByNumber(number)
                .map(flightMapper::entityToResponse)
                .orElseThrow(() -> new FlightNotFoundException(number));

        log.info("Flight returned : {}", response);
        return response;
    }

    public FlightResponse create(FlightRequest request) {
        if (existsByNumber(request.getNumber())) {
            throw new FlightAlreadyExistsException(request.getNumber());
        }
        var response = flightMapper.entityToResponse(
                flightRepository.save(flightMapper.requestToEntity(request))
        );

        log.info("Flight created : {}", response);
        return response;
    }

    public FlightResponse update(FlightRequest request) {
        var response = flightRepository.findByNumber(request.getNumber())
                .map(flight -> flightMapper.updateEntityFromRequest(request, flight))
                .map(flightMapper::entityToResponse)
                .orElseGet(() -> flightMapper.entityToResponse(
                        flightRepository.save(flightMapper.requestToEntity(request))
                ));

        log.info("Flight updated : {}", response);
        return response;
    }

    public void deleteByNumber(String number) {
        flightRepository.deleteByNumber(number);
        log.info("Flight with number {} deleted", number);
    }

    private boolean existsByNumber(String number) {
        return flightRepository.existsByNumber(number);
    }

}
