package com.daos.service;

import com.daos.persistence.dao.FlightDAO;
import com.daos.persistence.dao.SeatDAO;
import com.daos.persistence.entity.CityEntity;
import com.daos.persistence.entity.FlightEntity;
import com.daos.persistence.entity.SeatEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightDAO flightDAO;

    @Autowired
    private SeatDAO seatDAO;

    /**
     * Inserta un nuevo vuelo
     * @param flight FlightEntity_same
     * @throws Exception excepción
     */
    public void insert(FlightEntity flight) throws Exception {
        if (getByFlightNumber(flight.getFlightNumber()) != null)
            throw new Exception("El vuelo ya existe");
        else
            flightDAO.save(flight);
    };

    /**
     * Actualiza un vuelo
     *
     * @param flightNumber Número de vuelo del vuelo a actualizar
     * @param flight Datos actualizados del vuelo
     * @return El vuelo actualizado
     */
    public FlightEntity update(Long flightNumber, FlightEntity flight) {
        FlightEntity flightToUpdate = getByFlightNumber(flightNumber);

        if (flightToUpdate != null) {
            if (flight.getDepartureDateTime() != null) {
                flightToUpdate.setDepartureDateTime(flight.getDepartureDateTime());
            }
            if (flight.getSeatsColumnsInPlane() != null) {
                flightToUpdate.setSeatsColumnsInPlane(flight.getSeatsColumnsInPlane());
            }
            if (flight.getSeatsRowsInPlane() != null) {
                flightToUpdate.setSeatsRowsInPlane(flight.getSeatsRowsInPlane());
            }
            if (flight.getDestination() != null) {
                flightToUpdate.setDestination(flight.getDestination());
            }
            if (flight.getFlightStatus() != null) {
                flightToUpdate.setFlightStatus(flight.getFlightStatus());
            }
            if (flight.getIsInternational() != null) {
                flightToUpdate.setIsInternational(flight.getIsInternational());
            }
            if (flight.getDefaultCost() != null) {
                flightToUpdate.setDefaultCost(flight.getDefaultCost());
            }
            flightDAO.save(flightToUpdate);
        }
        return flightToUpdate;
    }

    /**
     * Elimina un vuelo
     * @param flightNumber Número de vuelo
     */
    public void delete(Long flightNumber)  {
        flightDAO.deleteById(flightNumber);
    }

    /**
     * Devuelve la lista completa de vuelos
     * @return List<FlightEntity_same>
     */
    public List<FlightEntity> getAll()  {
        return flightDAO.findAll();
    }

    /**
     * Obtiene los datos de un vuelo a partir del nro de vuelo
     * @param flightNumber Número de vuelo
     * @return FlightEntity_same
     */
    public FlightEntity getByFlightNumber(Long flightNumber)  {
        return flightDAO.findByFlightNumber(flightNumber);
    }

    public boolean isSeatAvailable(Long seatId) {
        SeatEntity seat = seatDAO.findBySeatId(seatId);
        return seat.isAvailable();
    }

    public List<String> getAllSeatsByFlight(FlightEntity flight) {
        return seatDAO.getAllSeatsByFlight(flight);
    }

    public List<FlightEntity> getByDestination(CityEntity city) {
        return flightDAO.findByDestinationAndDepartureDateTimeAfter(city, LocalDateTime.now());
    }
}