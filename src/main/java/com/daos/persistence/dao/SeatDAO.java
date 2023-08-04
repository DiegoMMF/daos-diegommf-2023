package com.daos.persistence.dao;

import com.daos.persistence.entity.FlightEntity;
import com.daos.persistence.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatDAO extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findByFlight(FlightEntity flight);

    SeatEntity findBySeatId(Long seatId);

    List<SeatEntity> findByFlightAndPassengerIsNull(FlightEntity flight);

    List<String> getAllSeatsByFlight (FlightEntity flight);
}
