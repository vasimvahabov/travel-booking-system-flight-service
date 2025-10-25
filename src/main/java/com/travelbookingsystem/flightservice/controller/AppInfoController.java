package com.travelbookingsystem.flightservice.controller;

import com.travelbookingsystem.flightservice.config.AppInfoProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppInfoController {

    AppInfoProperties appInfoProperties;

    @GetMapping("/info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok(String.format("%s %s in %s environment", appInfoProperties.getName(), appInfoProperties.getVersion(), appInfoProperties.getEnvironment()));
    }

}
