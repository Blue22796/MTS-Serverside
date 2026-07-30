package com.example.main.repositories;

import com.example.main.models.Ticket;
import com.example.main.models.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByTechnicianIsNotNullAndScheduledVisitIsNotNull();
    List<Ticket> findByTechnicianId(Long technicianId);
    List<Ticket> findByTechnicianIdAndScheduledVisitIsNotNull(Long technicianId);
    List<Ticket> findByTechnicianIdAndScheduledVisit(Long technicianId, LocalDateTime scheduledVisit);
    List<Ticket> findByTechnicianIdAndScheduledVisitAndStatus(Long technicianId, LocalDateTime scheduledVisit, TicketStatus status);
    List<Ticket> findByCustomerPhoneAndStatus(String customerPhone, TicketStatus status);
}
