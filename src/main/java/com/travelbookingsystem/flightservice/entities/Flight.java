package com.travelbookingsystem.flightservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import static com.travelbookingsystem.flightservice.config.ApplicationConstants.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flights")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Flight {

    @Id
    @Column(name = "id")
    Long id;

    @Version
    @Builder.Default
    @Column(name = "version")
    Integer version = 0;

    @Column(name = "airplane_id")
    @NotNull(message = "The airplane id cannot be null!")
    Long airplaneId;

    @Column(name = "number")
    @Pattern(
            regexp = "^[A-Z]{2}\\d{1,4}$",
            message = "The flight number is not valid!"
    )
    @NotBlank(message = "The flight number cannot be blank!")
    String number;

    @Column(name = "departure_airport_code")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "The departure airport code number must be 3 uppercase letters (IATA format)!"
    )
    @NotBlank(message = "The departure airport code title cannot be blank!")
    String departureAirportCode;

    @Column(name = "arrival_airport_code")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "The arrival airport code must be 3 uppercase letters (IATA format)!"
    )
    @NotBlank(message = "The arrival airport code title cannot be blank!")
    String arrivalAirportCode;

    @Column(name = "departure_date_time")
    @NotNull(message = "The departure date time cannot be blank!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    LocalDateTime departureDateTime;

    @Column(name = "arrival_date_time")
    @NotNull(message = "The arrival date time cannot be blank!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    LocalDateTime arrivalDateTime;


    @CreatedDate
    @Builder.Default
    @Column(name = "created_date_time")
    Instant createdDateTime = Instant.now();

    @Builder.Default
    @LastModifiedDate
    @Column(name = "last_modified_date_time")
    Instant lastModifiedDateTime = Instant.now();


    @Column(name = "price")
    @NotNull(message = "The flight price cannot be null!")
    @Positive(message = "The flight price cannot be negative!")
    BigDecimal price;

}
