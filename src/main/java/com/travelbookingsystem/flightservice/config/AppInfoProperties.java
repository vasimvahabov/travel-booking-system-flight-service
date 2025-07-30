package com.travelbookingsystem.flightservice.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
//@RefreshScope
//@EnableConfigurationProperties(FlightServiceProperties.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "application")
public class AppInfoProperties {

    String name;

    String version;

    String environment;

}
