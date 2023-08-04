package com.daos.persistence.dao;

import com.daos.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerDAO extends JpaRepository<CustomerEntity, Long> {
    List<CustomerEntity> findByLastNameAndFirstName(String lastName, String firstName);
    CustomerEntity findByCustomerDNI(Long customerDNI);
}
