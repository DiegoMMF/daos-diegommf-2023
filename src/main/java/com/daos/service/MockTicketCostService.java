package com.daos.service;

import com.daos.persistence.entity.FlightEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MockTicketCostService {
    public double calculatePrice (FlightEntity flight) {
        // Implementación ficticia para calcular el costo del pasaje
        return 100.0; // Cambia este valor según tus necesidades
    }
}