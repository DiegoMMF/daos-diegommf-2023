package com.daos.persistence.dao;

import com.daos.persistence.entity.CityEntity;
import com.daos.persistence.entity.FlightEntity;
import com.daos.persistence.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightDAO extends JpaRepository<FlightEntity, Long> {
    FlightEntity findByFlightNumber(Long flightNumber);
    List<FlightEntity> findByDestinationAndDepartureDateTimeAfter(CityEntity city, LocalDateTime departureDateTime);
}
