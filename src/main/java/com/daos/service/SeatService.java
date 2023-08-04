package com.daos.service;

import com.daos.persistence.dao.SeatDAO;
import com.daos.persistence.entity.CustomerEntity;
import com.daos.persistence.entity.FlightEntity;
import com.daos.persistence.entity.SeatEntity;
import com.daos.persistence.entity.TicketEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatDAO seatDAO;

    public SeatEntity assignSeat(
            Long idSeat,
            TicketEntity ticket,
            CustomerEntity customer
    ) {
        seatDAO.findById(idSeat)
            .ifPresentOrElse(
                seat -> {
                    seat.setTicket(ticket);
                    seat.setPassenger(customer);
                    seatDAO.save(seat);
                },
                () -> {
                    throw new IllegalArgumentException("Seat not found");
                }
            );
        return seatDAO.findById(idSeat).get();
    }

    public SeatEntity unassignSeat(Long idSeat) {
        seatDAO.findById(idSeat)
            .ifPresentOrElse(
                seat -> {
                    seat.setTicket(null);
                    seat.setPassenger(null);
                    seatDAO.save(seat);
                },
                () -> {
                    throw new IllegalArgumentException("Seat not found");
                }
            );
        return seatDAO.findById(idSeat).get();
    }

    public List<SeatEntity> getSeatsByFlight(FlightEntity flight) {
        return seatDAO.findByFlight(flight);
    }

    public SeatEntity getSeatById(Long seatId) {
        return seatDAO.findBySeatId(seatId);
    }

    public List<SeatEntity> getAllInDatabase() {
        return seatDAO.findAll();
    }
}
