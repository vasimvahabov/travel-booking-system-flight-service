package com.travelbookingsystem.flightservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flights")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Flight {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "gen_flight_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "gen_flight_id", sequenceName = "seq_flight_id", allocationSize = 1)
    Long id;

    @Version
    @Builder.Default
    @Column(name = "version")
    Integer version = 0;

    @Column(name = "airplane_id")
    Long airplaneId;

    @Column(name = "number")
    String number;

    @Column(name = "departure_airport_code")
    String departureAirportCode;

    @Column(name = "arrival_airport_code")
    String arrivalAirportCode;

    @Column(name = "departure_date_time")
    LocalDateTime departureDateTime;

    @Column(name = "arrival_date_time")
    LocalDateTime arrivalDateTime;

    @CreatedDate
    @Column(name = "created_date_time")
    Instant createdDateTime;

    @LastModifiedDate
    @Column(name = "last_modified_date_time")
    Instant lastModifiedDateTime;

    @Column(name = "price")
    BigDecimal price;

    @CreatedBy
    @Column(name = "created_by")
    UUID createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    UUID lastModifiedBy;

}
