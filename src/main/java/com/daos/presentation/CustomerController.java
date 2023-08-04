package com.daos.presentation;

import com.daos.persistence.entity.CustomerEntity;
import com.daos.presentation.dto.CustomerDTO;
import com.daos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Obtiene todos los clientes
     * curl --location --request GET 'localhost:8080/customers'
     *
     * @return Lista de clientes
     */
    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<CustomerEntity>> getAll() {
        return new ResponseEntity<>(customerService.getAll(), HttpStatus.OK);
    }

    /**
     * Obtiene un cliente a través de su dni.
     * curl --location --request GET 'localhost:8080/customers/dni/12345678'
     *
     * @param dni DNI del cliente
     * @return CustomerEntity
     * @throws Exception Si el cliente no existe
     */
    @GetMapping(
            value = "/dni/{dni}",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CustomerEntity> getByCustomerDNI(@PathVariable Long dni) throws Exception {
        CustomerEntity customer = customerService.getByCustomerDNI(dni);
        if (customer != null) {
            return new ResponseEntity<CustomerEntity>(customer, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Obtiene un cliente a través de su id.
     * curl --location --request GET 'localhost:8080/customers/1'
     *
     * @param id Id del cliente
     * @return CustomerEntity
     * @throws Exception Si el cliente no existe
     */
    @GetMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CustomerEntity> getById(@PathVariable Long id) throws Exception {
        CustomerEntity customer = customerService.getById(id);
        if (customer != null) {
            return new ResponseEntity<CustomerEntity>(customer, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Inserta un nuevo cliente.
     * curl --location --request POST 'localhost:8080/customers/new'
     *
     * @param customer Cliente a insertar
     *  {
     *     "customerDNI": 12345671,
     *     "firstName": "Roberto",
     *     "lastName": "Gómez Gómez",
     *     "email": "r.g.gomez@example.com",
     *     "phoneNumber": 1122334455,
     *     "birthDate": "1990-01-15T00:00:00",
     *     "passportNumber": "AB123456DD",
     *     "passportExpirationDate": "2024-12-31T21:00:00"
     * }
     * return CustomerEntity
     */
    @PostMapping(
            value = "/new",
            consumes = { MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CustomerEntity> create(
            @RequestBody CustomerEntity customer
    ) throws Exception {
        try {
            CustomerEntity response = customerService.insert(customer);
            return new ResponseEntity<CustomerEntity>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Actualiza un cliente.
     * curl --location --request PUT 'localhost:8080/customers/1'
     * --header 'Content-Type: application/json'
     * --data-raw '{
     *     "customerDNI": 12345671,
     *     "firstName": "Roberto"
     * }
     *
     * @param id Id del cliente
     * @param customerDTO Cliente a actualizar
     * @return CustomerEntity
     * @throws Exception Si el cliente no existe
     */
    @PutMapping(
            value = "/edit/{id}",
            consumes = { MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CustomerEntity> update(
            @PathVariable Long id,
            @RequestBody CustomerDTO customerDTO
    ) throws Exception {
        try {
            CustomerEntity customer = customerService.getById(id);
            if (customer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            CustomerEntity response = customerService.update(id, customerDTO);
            return new ResponseEntity<CustomerEntity>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Elimina un cliente.
     * curl --location --request DELETE 'localhost:8080/customers/1'
     *
     * @param id Id del cliente
     * @return CustomerEntity
     * @throws Exception Si el cliente no existe
     */
    @DeleteMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CustomerEntity> delete(@PathVariable Long id) throws Exception {
        try {
            customerService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

