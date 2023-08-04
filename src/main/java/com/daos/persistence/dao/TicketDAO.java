package com.daos.persistence.dao;

import com.daos.persistence.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDAO extends JpaRepository<TicketEntity, Long> {
}
