package com.example.main.services;

import com.example.main.models.Ticket;
import com.example.main.models.TicketCategory;
import com.example.main.models.TicketStatus;
import com.example.main.models.dtos.TicketCreateDTO;
import com.example.main.models.dtos.TicketViewDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketMapper {

    public TicketViewDTO ticketToView(Ticket ticket) {
        return TicketViewDTO.builder()
                .id(ticket.getId())
                .customerName(ticket.getCustomerName())
                .customerAddress(ticket.getCustomerAddress())
                .customerPhone(ticket.getCustomerPhone())
                .category(ticket.getCategory().name())
                .notes(ticket.getNotes())
                .proposedDate(ticket.getProposedDate())
                .status(ticket.getStatus().name())
                .scheduledDate(
                        ticket.getScheduledVisit() == null
                                ? null
                                : ticket.getScheduledVisit())
                .calls(ticket.getCalls())
                .creationDate(ticket.getCreationDate())
		.technicianName(ticket.getTechnician() == null? null : ticket.getTechnician().getName())
                .build();
    }

    public Ticket createTicket(TicketCreateDTO dto) {
        return Ticket.builder()
                .customerName(dto.getCustomerName())
                .customerAddress(dto.getCustomerAddress())
                .customerPhone(dto.getCustomerPhone())
                .customerEmail(dto.getCustomerEmail())
                .category(TicketCategory.valueOf(dto.getCategory()))
                .notes(dto.getNotes())
                .proposedDate(dto.getProposedDate())
                .creationDate(LocalDateTime.now())
                .calls(0)
                .status(TicketStatus.PENDING)
                .build();
    }
}
