package com.travelbookingsystem.flightservice.service;

import com.travelbookingsystem.flightservice.dto.request.FlightRequest;
import com.travelbookingsystem.flightservice.dto.response.FlightResponse;

import java.util.List;

public interface FlightService {

    List<FlightResponse> findAll();

    FlightResponse findByNumber(String number);

    FlightResponse create(FlightRequest request);

    FlightResponse update(FlightRequest request);

    void deleteByNumber(String number);

}
