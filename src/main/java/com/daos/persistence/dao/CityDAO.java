package com.daos.persistence.dao;

import com.daos.persistence.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDAO extends JpaRepository<CityEntity, Long> {
}
