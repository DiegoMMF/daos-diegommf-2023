package com.daos.presentation;

import com.daos.persistence.entity.CustomerEntity;
import com.daos.persistence.entity.FlightEntity;
import com.daos.persistence.entity.SeatEntity;
import com.daos.persistence.entity.TicketEntity;
import com.daos.service.CustomerService;
import com.daos.service.FlightService;
import com.daos.service.SeatService;
import com.daos.service.TicketService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CustomerService customerService;

    /**
     * Obtiene todos los asientos.
     * curl --location --request GET 'localhost:8080/seats'
     *
     * @return Lista de asientos
     */
    @GetMapping(
            value = "/all-seats-in-database",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<com.daos.persistence.entity.SeatEntity>> getAllSeats() {
        List<com.daos.persistence.entity.SeatEntity> seats = seatService.getAllInDatabase();
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    /**
     * Obtiene todos los asientos de un vuelo.
     * curl --location --request GET 'localhost:8080/seats?flightNumber=123456'
     *
     * @param flightNumber Número de vuelo
     * @return Lista de asientos del vuelo
     */
    @GetMapping(
            value = "/flight/{flightNumber}",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<com.daos.persistence.entity.SeatEntity>> getSeatsByFlight(@PathVariable Long flightNumber) {
        FlightEntity flight = flightService.getByFlightNumber(flightNumber);
        System.out.println("flightNumber: " + flight + " - flight:" + flight);
        if (flight != null) {
            List<com.daos.persistence.entity.SeatEntity> seats = seatService.getSeatsByFlight(flight);
            return new ResponseEntity<>(seats, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Asigna un asiento a un cliente mediante un ticket.
     * curl --location --request PUT 'localhost:8080/seats/1/assign/123456/789456123'
     * --header 'Content-Type: application/json'
     *
     * @param seatId Id del asiento
     * @param customerId Id del cliente
     * @param ticketId  Id del ticket
     * @return SeatEntity
     */
    @PutMapping(
            value = "/{seatId}/assign/{ticketId}/{customerId}",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<SeatEntity> assignSeat(
            @PathVariable Long seatId,
            @PathVariable Long ticketId,
            @PathVariable Long customerId
    ) throws Exception {
        try {
            // veo si existe el asiento
            SeatEntity seatEntity = seatService.getSeatById(seatId);
            if (seatEntity == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // veo si existe el ticket
            TicketEntity ticketEntity = ticketService.getByTicketId(ticketId);
            if (ticketEntity == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // veo si existe el cliente
            CustomerEntity customerEntity = customerService.getById(customerId);
            if (customerEntity == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            SeatEntity response = seatService.assignSeat(
                    seatId,
                    ticketEntity,
                    customerEntity
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Desasigna un asiento de un cliente y ticket.
     * curl --location --request DELETE 'localhost:8080/seats/unassign/1'
     *
     * @param seatId Id del asiento a desasignar
     * @return SeatEntity
     */
    @DeleteMapping(
            value = "/unassign/{seatId}",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<com.daos.persistence.entity.SeatEntity> unassignSeat(@PathVariable Long seatId) {
        try {
            com.daos.persistence.entity.SeatEntity seat = seatService.unassignSeat(seatId);
            return new ResponseEntity<>(seat, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
