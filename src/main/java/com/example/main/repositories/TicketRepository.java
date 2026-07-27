package com.example.main.repositories;

import com.example.main.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByTechnicianIsNotNullAndScheduledVisitIsNotNull();

}
