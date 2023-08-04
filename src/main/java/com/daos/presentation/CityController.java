package com.daos.presentation;

import com.daos.persistence.entity.CityEntity;
import com.daos.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * Obtiene todas las ciudades.
     * curl --location --request GET 'localhost:8080/cities'
     *
     * @return Lista de ciudades
     */
    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Iterable<CityEntity>> findAll() {
        Iterable<CityEntity> rta = cityService.getAll();
        return new ResponseEntity<Iterable<CityEntity>>(rta, HttpStatus.OK);
    }

    /**
     * Obtiene una ciudad a través de su id.
     * curl --location --request GET 'localhost:8080/cities/3'
     *
     * @param id Id de la ciudad
     * @return CityEntity
     * @throws Exception Si la ciudad no existe
     */
    @GetMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CityEntity> getById(@PathVariable Long id) throws Exception {
        Optional<CityEntity> city = cityService.getById(id);
        if(city.isPresent()) {
            CityEntity pojo = city.get();
            return new ResponseEntity<CityEntity>(pojo, HttpStatus.OK);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Crea una ciudad.
     * curl --location --request POST 'localhost:8080/cities/new'
     * --header 'Content-Type: application/json'
     * --data-raw '{
     *    "cityName": "Bogotá",
     *    "zipCode": "COL123"
     *    }'
     *
     *    @param city CityEntity
     */
    @PostMapping(
            value = "/new",
            consumes = { MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CityEntity> create(@RequestBody CityEntity city) {
        CityEntity rta = cityService.insert(city);
        return new ResponseEntity<CityEntity>(rta, HttpStatus.CREATED);
    }
}
