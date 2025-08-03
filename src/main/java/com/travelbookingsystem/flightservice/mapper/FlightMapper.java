package com.travelbookingsystem.flightservice.mapper;

import com.travelbookingsystem.flightservice.dtos.request.FlightRequest;
import com.travelbookingsystem.flightservice.dtos.response.FlightResponse;
import com.travelbookingsystem.flightservice.entities.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FlightMapper {

    FlightResponse entityToResponse(Flight flight);

    Flight requestToEntity(FlightRequest flightRequest);

    Flight updateEntityFromRequest(FlightRequest request, @MappingTarget Flight flight);

}
