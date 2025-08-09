package com.travelbookingsystem.flightservice.mapper;

import com.travelbookingsystem.flightservice.dtos.request.FlightRequest;
import com.travelbookingsystem.flightservice.dtos.response.FlightResponse;
import com.travelbookingsystem.flightservice.entities.Flight;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FlightMapper {

    FlightResponse entityToResponse(Flight flight);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "lastModifiedDateTime", ignore = true)
    Flight requestToEntity(FlightRequest flightRequest);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "lastModifiedDateTime", ignore = true)
    Flight updateEntityFromRequest(FlightRequest request, @MappingTarget Flight flight);

}
