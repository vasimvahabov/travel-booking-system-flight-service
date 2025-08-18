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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlightService {

    FlightRepository flightRepository;
    FlightMapper flightMapper;

    public List<FlightResponse> findAll() {
        return flightRepository.findAll().stream().map(flightMapper::entityToResponse).toList();
    }

    public FlightResponse findByNumber(String number) {
        return flightRepository.findByNumber(number)
                .map(flightMapper::entityToResponse)
                .orElseThrow(() -> new FlightNotFoundException(number));
    }

    public boolean existsByNumber(String number) {
        return flightRepository.existsByNumber(number);
    }

    public FlightResponse create(FlightRequest request) {
        if (existsByNumber(request.getNumber())) {
            throw new FlightAlreadyExistsException(request.getNumber());
        }
        return flightMapper.entityToResponse(flightRepository.save(flightMapper.requestToEntity(request)));
    }

    public FlightResponse update(FlightRequest request) {
        return flightRepository.findByNumber(request.getNumber())
                .map(flight -> flightMapper.updateEntityFromRequest(request, flight))
                .map(flightMapper::entityToResponse)
                .orElseGet(() -> flightMapper.entityToResponse(
                        flightRepository.save(flightMapper.requestToEntity(request))
                ));
    }

    public void deleteByNumber(String number) {
        flightRepository.deleteByNumber(number);
    }

}
