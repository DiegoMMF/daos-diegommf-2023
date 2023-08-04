package com.daos.service;

import com.daos.persistence.entity.FlightEntity;
import com.daos.persistence.entity.TicketEntity;

public class TicketCostService {
    public double calculatePrice (FlightEntity flight) {
        Double defaultCost = flight.getDefaultCost();

        // aquí van las reglas de negocio
        if (flight.getIsInternational()) {
            return defaultCost *= 1.1;
        } else {
            return defaultCost *= 0.9;
        }
    }
}
