package com.daos.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DollarProxy {
    /**
     * Devuelve la lista de cotizaciones del día
     * @return Lista de cotizaciones
     */
    public List<DollarQuotation> getDollarQuotations() {
        List<DollarQuotation> dollarQuotations = new ArrayList<DollarQuotation>();

        ResponseEntity<DollarExchangeRate[]> responseEntity = new RestTemplate().getForEntity(
                "https://www.dolarsi.com/api/api.php?type=valoresprincipales",
                DollarExchangeRate[].class
        );
        for (DollarExchangeRate dollarExchangeRate : Objects.requireNonNull(responseEntity.getBody())) {
            dollarQuotations.add(dollarExchangeRate.getRate());
        }
        return dollarQuotations;
    };
}