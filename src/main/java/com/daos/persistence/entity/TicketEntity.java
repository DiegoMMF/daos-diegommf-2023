package com.daos.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ticket_id",
            nullable = false,
            unique = true
    )
    private Long ticketId;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(
            name = "flight",
            referencedColumnName = "flight_number",
            nullable = false
    )
    private FlightEntity flight;

    @OneToOne
    @JoinColumn(
            name = "customer",
            referencedColumnName = "customer_id",
            nullable = false
    )
    private CustomerEntity customer;

    @OneToOne
    @JoinColumn(
            name = "seat",
            referencedColumnName = "seat_id"
    )
    private SeatEntity seat;
}

