package com.travelbookingsystem.flightservice.controller;

import com.travelbookingsystem.flightservice.config.AppInfoProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppInfoController {

    static final Logger log = LoggerFactory.getLogger(AppInfoController.class);

    AppInfoProperties appInfoProperties;

    @GetMapping("/info")
    public ResponseEntity<String> info() {
        log.info("Fetching app info ...");
        return ResponseEntity.ok(String.format("%s %s in %s environment", appInfoProperties.getName(), appInfoProperties.getVersion(), appInfoProperties.getEnvironment()));
    }

}
