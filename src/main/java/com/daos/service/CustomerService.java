package com.daos.service;

import com.daos.persistence.dao.CustomerDAO;
import com.daos.persistence.entity.CustomerEntity;
import com.daos.presentation.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerDAO customerDAO;

    /**
     * Devuelve la lista completa de clientes
     * @return Lista de clientes
     */
    public List<CustomerEntity> getAll() {
        return customerDAO.findAll();
    }

    /**
     * Obtiene una persona a partir de su identidicador
     * @param dni dni de la persona a obtener
     * @return persona
     */
    public CustomerEntity getByCustomerDNI(Long dni) {
        return customerDAO.findByCustomerDNI(dni);
    }


    /**
     * Obtiene una persona a partir de su identidicador
     * @param id id de la persona a obtener
     * @return persona
     */
    public CustomerEntity getById(Long id) {
        if (customerDAO.findById(id).isPresent())
            return customerDAO.findById(id).get();
        else
            return null;
    }

    /**
     * Actualiza datos de una persona
     *
     * @param customerDTO persona a actualizar
     * @return persona actualizada
     */
    public CustomerEntity update(Long customerId, CustomerDTO customerDTO) {
        CustomerEntity customerToUpdate = getById(customerId);
        if (customerToUpdate != null) {
            if (customerDTO.getFirstName() != null)
                customerToUpdate.setFirstName(customerDTO.getFirstName());
            if (customerDTO.getLastName() != null)
                customerToUpdate.setLastName(customerDTO.getLastName());
            if (customerDTO.getPhoneNumber() != null)
                customerToUpdate.setPhoneNumber(customerDTO.getPhoneNumber());
            if (customerDTO.getEmail() != null)
                customerToUpdate.setEmail(customerDTO.getEmail());
            if (customerDTO.getPassport() != null)
                customerToUpdate.setPassportNumber(customerDTO.getPassport());
            if (customerDTO.getPassportExpirationDate() != null)
                customerToUpdate.setPassportExpirationDate(customerDTO.getPassportExpirationDate());
            if (customerDTO.getBirthDate() != null)
                customerToUpdate.setBirthDate(customerDTO.getBirthDate());
            if (customerDTO.getAddress() != null) {
                // convierto la entidad AddressEntity a Long para meterla en el DTO
                customerDTO.setAddress(customerToUpdate.getAddress().getAddressId());
            }
            customerDAO.save(customerToUpdate);
        }
        return customerToUpdate;
    }

    /**
     * Inserta una nueva persona
     *
     * @param customer persona a insertar
     * @return customer persona insertada
     */
    public CustomerEntity insert(CustomerEntity customer) {
        customerDAO.save(customer);
        return customer;
    }

    /**
     * Elimina una persona del sistema
     * @param id dni de la persona a eliminar
     */
    public void delete(Long id) {
        customerDAO.deleteById(id);
    }

    /**
     * Filtra la lista de personas por apellido y/o nombre
     * @param apellido apellido de la persona
     * @param nombre nombre de la persona
     * @return lista de personas filtrada
     */
    public List<CustomerEntity> filter(String apellido, String nombre) {
        if (apellido == null && nombre == null)
            return customerDAO.findAll();
        else
            return customerDAO.findByLastNameAndFirstName(apellido, nombre);
    }

    /**
     * Verifica si existe una persona con el dni indicado
     * @param dni dni de la persona a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean doesCustomerExist(Long dni) {
        return customerDAO.findByCustomerDNI(dni) != null;
    }
}
