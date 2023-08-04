package com.daos.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flight")
@Getter
@Setter
@NoArgsConstructor
public class FlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "flight_number",
            nullable = false,
            unique = true
    )
    private Long flightNumber;

    @Column(
            name = "departure_date_time",
            columnDefinition = "DATETIME",
            nullable = false
    )
    private LocalDateTime departureDateTime;

    @Column(
            name = "seat_columns_in_plane",
            nullable = false
    )
    private Integer seatsColumnsInPlane;

    @Column(
            name = "seat_rows_in_plane",
            nullable = false
    )
    private Integer seatsRowsInPlane;

    @ManyToOne
    @JoinColumn(
            name = "destination",
            referencedColumnName = "city_id",
            nullable = false
    )
    private CityEntity destination;

    @Column(nullable = false, name = "flight_status")
    private String flightStatus;

    @Column(name = "is_international")
    private Boolean isInternational;

    @Column(name = "default_cost")
    private Double defaultCost;
}
