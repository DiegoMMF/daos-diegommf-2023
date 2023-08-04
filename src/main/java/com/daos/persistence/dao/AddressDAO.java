package com.daos.persistence.dao;

import com.daos.persistence.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDAO extends JpaRepository<AddressEntity, Long> {
}
