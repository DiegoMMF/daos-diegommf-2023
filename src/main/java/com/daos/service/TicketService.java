package com.daos.service;

import com.daos.persistence.dao.CustomerDAO;
import com.daos.persistence.dao.FlightDAO;
import com.daos.persistence.dao.SeatDAO;
import com.daos.persistence.dao.TicketDAO;
import com.daos.persistence.entity.CustomerEntity;
import com.daos.persistence.entity.FlightEntity;
import com.daos.persistence.entity.SeatEntity;
import com.daos.persistence.entity.TicketEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class TicketService {
    @Autowired
    private final TicketDAO ticketDAO;

    @Autowired
    private final CustomerDAO customerDAO;

    @Autowired
    private final FlightDAO flightDAO;

    @Autowired
    private final SeatDAO seatDAO;

    @Autowired
    private final MockTicketCostService mockTicketCostService;

    /**
     * Devuelve la lista completa de reservas
     * @return List<TicketEntity>
     */
    public List<TicketEntity> getAll() {
        return ticketDAO.findAll();
    }

    /**
     * Contiene la lógica de negocio para crear una reserva
     *
     * @param flightNumber número de vuelo
     * @param customerDNI dni del cliente
     * @return TicketEntity
     * @throws Exception por validaciones incumplidas
     */
    public TicketEntity createTicket (
            Long flightNumber,
            Long customerDNI
    ) throws Exception {

        // Pregunto si existe el destino
        FlightEntity flight = flightDAO.findByFlightNumber(flightNumber);
        if (flight == null) {
            throw new Exception("El vuelo no existe");
        }

        // Pregunto si el flight ya partió
        LocalDateTime departureDateTime = flight.getDepartureDateTime();
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(departureDateTime)) {
            throw new Exception("El vuelo ya partió");
        }

        // Pregunto si existe el passenger
        CustomerEntity passenger = customerDAO.findByCustomerDNI(customerDNI);
        if (passenger == null) {
            throw new Exception("El cliente no existe");
        }

        // Pregunto si el flight es internacional
        Boolean isInternacional = flight.getIsInternational();

        System.out.println("isInternacional: " + isInternacional);

        if (isInternacional) {
            // Pregunto si el passenger tiene pasaporte
            if (passenger.getPassportNumber() == null) {
                throw new Exception("El passenger no tiene pasaporte");
            }
        }

        // Recupero los asientos del vuelo consultado
        List<SeatEntity> seatsInFlight = seatDAO.findByFlight(flight);

        List<SeatEntity> availableSeats = seatDAO.findByFlightAndPassengerIsNull(flight);

        System.out.println("availableSeats: " + availableSeats);

        if (availableSeats.isEmpty()) {
            throw new Exception("No hay asientos disponibles");
        }

        // Están dadas todas las condiciones para efectuar la reserva
        SeatEntity seatToBePurchased = availableSeats.get(0);

        seatToBePurchased.setPassenger(passenger);

        // Fijamos el precio de flight:
        // V. Nacional: Costo Pasaje + TAN + IVA
        // V. Internacional: Costo Pasaje * Cotización Dólar + TAI
        // La cotización del dólar es provista por el BCRA mediante una api REST.
        // La tasa aeroportuaria para vuelos internacionales y nacionales puede
        // ser consultada a AFIP mediante una api rest.
        // La tasa de IVA es del 21%.
        // Una vez realizado el cobro, se confirmará la reserva
        double precio = 100.0;
        double TAN = 1.1;
        double TAI = 1.2;
        double IVA = 1.21;
        double cotizacionDolar = 500.0;
        if (isInternacional) {
            precio = precio * TAI * cotizacionDolar;
        } else {
            precio = precio * TAN * IVA;
        }

        System.out.println("precio: " + precio);

        // Creo el ticket
        TicketEntity ticket = new TicketEntity();
        ticket.setFlight(flight);
        ticket.setCustomer(passenger);
        ticket.setSeat(seatToBePurchased);
        ticket.setPrice(precio);

        System.out.println("ticket: " + ticket);

        return ticketDAO.save(ticket);
    }

    public TicketEntity save(TicketEntity ticketEntity) {
        return this.ticketDAO.save(ticketEntity);
    }

    public TicketEntity getByTicketId (Long ticketId) {
        return this.ticketDAO.findById(ticketId).orElse(null);
    }

    public void deleteById(Long id) {
        this.ticketDAO.deleteById(id);
    }

    public TicketEntity update(TicketEntity ticketEntity) {
        return this.ticketDAO.save(ticketEntity);
    }
}

