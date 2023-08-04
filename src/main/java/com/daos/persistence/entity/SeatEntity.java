package com.daos.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "seat_id",
            nullable = false,
            unique = true
    )
    private Long seatId;

    @Column(name = "`row_number`", nullable = false)
    private Integer rowNumber;

    @Column(name = "column_number", nullable = false)
    private Integer columnNumber;

    @ManyToOne
    @JoinColumn(
            name = "flight_number",
            referencedColumnName = "flight_number",
            nullable = false
    )
    private FlightEntity flight;

    @OneToOne
    @JoinColumn(
            name = "customer",
            referencedColumnName = "customer_id"
    )
    private CustomerEntity passenger;

    @OneToOne
    @JoinColumn(
            name = "ticket",
            referencedColumnName = "ticket_id"
    )
    private TicketEntity ticket;

    public boolean isAvailable() {
        return this.passenger == null;
    }
}