package com.daos.presentation;

import com.daos.persistence.entity.CityEntity;
import com.daos.persistence.entity.FlightEntity;
import com.daos.service.CityService;
import com.daos.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private CityService cityService;

    /**
     * Obtiene todos los vuelos.
     * curl --location --request GET 'localhost:8080/flights'
     *
     * @return Lista de vuelos
     */
    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<FlightEntity>> getAllFlights() {
        List<FlightEntity> flights = flightService.getAll();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    /**
     * Obtiene un vuelo a través de su número de vuelo.
     * curl --location --request GET 'localhost:8080/flights/123'
     *
     * @param flightNumber Número de vuelo
     * @return FlightEntity
     * @throws Exception Si el vuelo no existe
     */
    @GetMapping(
            value = "/{flightNumber}",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<FlightEntity> getFlightByNumber(@PathVariable Long flightNumber) throws Exception {
        FlightEntity flight = flightService.getByFlightNumber(flightNumber);
        if (flight != null) {
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Crea un nuevo vuelo.
     * curl --location --request POST 'localhost:8080/flights/new'
     * --header 'Content-Type: application/json'
     * --data-raw '{
     *    "departureDateTime": "2023-08-03T17:00:00",
     *    "seatsColumnsInPlane": 4,
     *    "seatsRowsInPlane": 25,
     *    "destination": {
     *        "cityId": 1,
     *        "cityName": "Bogotá",
     *        "zipCode": "COL123"
     *    },
     *    "flightStatus": "Scheduled",
     *    "isInternational": true,
     *    "defaultCost": 250.0
     * }'
     *
     * @param flight FlightEntity
     * @return ResponseEntity con el vuelo creado
     */
    @PostMapping(
            value = "/new",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<FlightEntity> createFlight(@RequestBody FlightEntity flight) {
        try {
            flightService.insert(flight);
            return new ResponseEntity<>(flight, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Actualiza un vuelo.
     * curl --location --request PUT 'localhost:8080/flights/123'
     * --header 'Content-Type: application/json'
     * --data-raw '{
     *    "departureDateTime": "2023-08-03T18:30:00",
     *    "seatsColumnsInPlane": 4,
     *    "seatsRowsInPlane": 25,
     *    "destination": {
     *        "cityId": 1,
     *        "cityName": "Bogotá",
     *        "zipCode": "COL123"
     *    },
     *    "flightStatus": "Delayed",
     *    "isInternational": true,
     *    "defaultCost": 300.0
     * }'
     *
     * @param flightNumber Número de vuelo
     * @param flight FlightEntity
     * @return ResponseEntity con el vuelo actualizado
     * @throws Exception Si el vuelo no existe
     */
    @PutMapping(
            value = "/edit/{flightNumber}",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<FlightEntity> updateFlight(@PathVariable Long flightNumber, @RequestBody FlightEntity flight) throws Exception {
        try {
            FlightEntity response = flightService.update(flightNumber, flight);
            return new ResponseEntity<FlightEntity>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Elimina un vuelo.
     * curl --location --request DELETE 'localhost:8080/flights/123'
     *
     * @param flightNumber Número de vuelo
     * @return ResponseEntity
     * @throws Exception Si el vuelo no existe
     */
    @DeleteMapping(
            value = "/{flightNumber}",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Void> deleteFlight(@PathVariable Long flightNumber) throws Exception {
        try {
            flightService.delete(flightNumber);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Obtiene todos los vuelos que llegan a una ciudad.
     * curl --location --request GET 'localhost:8080/flights/destination/1'
     *
     * @param cityId Id de la ciudad
     */
    @GetMapping(
            value = "/destination/{cityId}",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<FlightEntity>> getFlightsByDestination(@PathVariable Long cityId) {
        Optional<CityEntity> optionalCity = cityService.getById(cityId);
        CityEntity city = optionalCity.orElse(new CityEntity());
        if (city.getCityId() != null) {
            List<FlightEntity> flights = flightService.getByDestination(city);
            return new ResponseEntity<>(flights, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
