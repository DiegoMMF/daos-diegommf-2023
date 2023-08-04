package com.daos.service;

import com.daos.persistence.dao.CityDAO;
import com.daos.persistence.entity.CityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private CityDAO cityDAO;

    /**
     * Obtiene la lista completa de ciudades
     * @return la lista completa de ciudades
     */
    public List<CityEntity> getAll() {
        return cityDAO.findAll();
    }

    /**
     * Obtiene una ciudad
     *
     * @param id identificador de la ciudad requerida
     * @return la ciudad requerida
     */
    public Optional<CityEntity> getById(Long id) {
        return cityDAO.findById(id);
    }

    /**
     * Inserta una nueva ciudad
     * @param city ciudad a insertar
     */
    public CityEntity insert(CityEntity city) {
        cityDAO.save(city);
        return city;
    }
}
