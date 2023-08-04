package com.daos.presentation;

import com.daos.persistence.entity.TicketEntity;
import com.daos.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    /**
     * Obtiene todos los tickets.
     * curl --location --request GET 'localhost:8080/tickets'
     *
     * @return Lista de tickets
     */
    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<TicketEntity>> getAll() {
        List<TicketEntity> tickets = ticketService.getAll();
        System.out.println("tickets: " + tickets);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    /**
     * Crea un nuevo ticket.
     * curl --location --request POST 'localhost:8080/tickets/new/{flightNumber}/{customerDNI}'
     * --header 'Content-Type: application/json'
     *
     * @param flightNumber Número de vuelo
     * @param customerDNI DNI del cliente
     *
     * @return TicketEntity
     */
    @PostMapping(
            value = "/new/{flightNumber}/{customerDNI}",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<TicketEntity> create(
            @PathVariable Long flightNumber,
            @PathVariable Long customerDNI
    ) throws Exception {
        try {
            TicketEntity ticket = ticketService.createTicket(
                    flightNumber,
                    customerDNI
            );
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Obtiene un ticket a través de su número de pasaje.
     * curl --location --request GET 'localhost:8080/tickets/1'
     *
     * @param ticketId Número de id del ticket
     * @return TicketEntity
     */
    @GetMapping(
            value = "/{ticketId}",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<TicketEntity> getByNroPasaje(@PathVariable Long ticketId) {
        TicketEntity ticket = ticketService.getByTicketId(ticketId);
        if (ticket != null) {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Actualiza un ticket.
     * curl --location --request PUT 'localhost:8080/tickets/1'
     * --header 'Content-Type: application/json'
     * --data-raw '{
     *     "price": 200.0
     * }'
     *
     * @param ticketId id del ticket a actualizar
     * @param receivedTicket Detalles actualizados del ticket
     * @return TicketEntity
     */
    @PutMapping(
            value = "/{ticketId}",
            consumes = { MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<TicketEntity> update(
            @PathVariable Long ticketId,
            @RequestBody TicketEntity receivedTicket
    ) {
        try {
            TicketEntity ticketToUpdate = ticketService.getByTicketId(ticketId);
            if (ticketToUpdate == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            ticketToUpdate.setPrice(receivedTicket.getPrice());
            TicketEntity updatedTicket = ticketService.update(ticketToUpdate);
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Elimina un ticket.
     * curl --location --request DELETE 'localhost:8080/tickets/1'
     *
     * @param ticketId Número de id del ticket a eliminar
     * @return TicketEntity
     */
    @DeleteMapping(
            value = "/{ticketId}",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<TicketEntity> delete(@PathVariable Long ticketId) {
        TicketEntity ticketToDelete = ticketService.getByTicketId(ticketId);
        if (ticketToDelete != null) {
            ticketService.deleteById(ticketId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
