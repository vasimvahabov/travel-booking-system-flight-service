package com.travelbookingsystem.flightservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
//@EnableConfigurationProperties(FlightServiceProperties.class)
@ConfigurationProperties(prefix = "spring.application")
public class AppInfoProperties {

    private String name;

    private String version;

}
