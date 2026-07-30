package com.example.main.services;

import com.example.main.models.Technician;
import com.example.main.models.Ticket;
import com.example.main.models.TicketStatus;
import com.example.main.models.dtos.TicketAssignDTO;
import com.example.main.models.dtos.TicketCreateDTO;
import com.example.main.models.dtos.TicketViewDTO;
import com.example.main.repositories.TechnicianRepository;
import com.example.main.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.main.models.dtos.TicketRescheduleDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TechnicianRepository technicianRepository;
    private final TicketMapper ticketMapper;
    private final ReservationService reservationService;

    private Ticket getVerifyTicket(Long ticketId){
        Ticket ticket = ticketRepository.getReferenceById(ticketId);
        if(ticket == null){
            throw new IllegalArgumentException("Ticket with this id does not exist");
        }
        return ticket;
    }

    public TicketViewDTO createTicket(TicketCreateDTO dto) {
        Ticket ticket = ticketMapper.createTicket(dto);
        List<Ticket> pendingTickets = ticketRepository.findByCustomerPhoneAndStatus(dto.customerPhone, TicketStatus.PENDING);
        List<Ticket>ticketsInProgress = ticketRepository.findByCustomerPhoneAndStatus(dto.customerPhone, TicketStatus.IN_PROGRESS);
        if(!pendingTickets.isEmpty()){
            throw new IllegalArgumentException("This customer already has a pending ticket");
        }
        if(!ticketsInProgress.isEmpty()){
            throw new IllegalArgumentException("This customer already has a ticket in progress");
        }
        if(dto.getProposedDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Proposed date has already passed");
        }
        if(dto.getProposedDate().isAfter(LocalDate.now().plusDays(14))){
            throw new IllegalArgumentException("Proposed date must be within 14 days of ticket creation");
        }
        ticket = ticketRepository.save(ticket);
        return ticketMapper.ticketToView(ticket);
    }

    public TicketViewDTO getTicket(Long id){
        Ticket ticket = getVerifyTicket(id);
        return ticketMapper.ticketToView(ticket);
    }

    public List<TicketViewDTO> getTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::ticketToView)
                .collect(Collectors.toList());
    }
    public void checkValidDate(Long ticketId, LocalDateTime visit){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        LocalDateTime latestAllowed = ticket.getCreationDate().plusDays(14);
        if (visit.isBefore(ticket.getCreationDate()) || visit.isAfter(latestAllowed)) {
            throw new IllegalArgumentException(
                    "Visit date must be within 14 days of the ticket creation date."
            );
        }
    }
    public void assignTicket(TicketAssignDTO dto) {
        Long ticketId = dto.ticketId;
        Long technicianId = dto.technicianId;
        checkValidDate(dto.ticketId, dto.vistDate);
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket not found.");
        }

        Technician technician = technicianRepository.findById(technicianId).orElse(null);
        if (technician == null) {
            throw new IllegalArgumentException("Technician not found.");
        }

        boolean isAvailable = reservationService.technicianIsAvailable(technicianId, dto.vistDate);


        if (!isAvailable) {
            throw new IllegalArgumentException(
                    "Technician is busy on " + dto.vistDate
            );
        }
        if(ticket.getTechnician() != null){
            throw new IllegalArgumentException("Ticket is already assigned to a technician");
        }
        ticket.setScheduledVisit(dto.vistDate);
        ticket.setTechnician(technician);
        ticket.setStatus(TicketStatus.IN_PROGRESS);
        ticketRepository.save(ticket);
    }

    public void reassignTicket(TicketAssignDTO dto){
        Long tickId = dto.ticketId;
        Long techId = dto.technicianId;
        checkValidDate(dto.ticketId, dto.vistDate);
        Ticket ticket = ticketRepository.findById(tickId).orElse(null);
        if(ticket == null){
            throw new IllegalArgumentException("Ticket not found.");
        }
        if(ticket.getTechnician() == null){
            throw new IllegalArgumentException("This ticket has not been assigned to a technician");
        }
        if(ticket.getTechnician().getId().equals(techId)){
            throw new IllegalArgumentException("Ticket has already been assigned to this technician");
        }
        Technician tech = technicianRepository.findById(techId).orElse(null);
        if(tech == null){
            throw new IllegalArgumentException("Technician does not exist");
        }
        if(!reservationService.technicianIsAvailable(techId, dto.vistDate)){
            throw new IllegalArgumentException("Technician is not available on "+dto.vistDate);
        }
        ticket.setScheduledVisit(dto.vistDate);
        ticket.setTechnician(tech);
        ticketRepository.save(ticket);
    }
    public void cancelTicket(Long id){
        Ticket ticket = getVerifyTicket(id);
        if(ticket.getStatus().equals(TicketStatus.CLOSED)){
            throw new IllegalArgumentException("Ticket has already been closed.");
        }
        if(ticket.getStatus().equals(TicketStatus.CANCELLED)){
            throw new IllegalArgumentException("Ticket has already been cancelled");
        }
        ticket.setStatus(TicketStatus.CANCELLED);
        ticket.setScheduledVisit(null);
        ticketRepository.save(ticket);
    }
    public void rescheduleTicket(TicketRescheduleDTO dto){
        Long ticketId = dto.ticketId;
        LocalDateTime visitDate = dto.visitDate;
        Ticket ticket = getVerifyTicket(ticketId);
        if(ticket.getScheduledVisit() == null || !ticket.getStatus().equals(TicketStatus.IN_PROGRESS)){
            throw new IllegalArgumentException("Ticket has not been scheduled before");
        }
        ticket.setScheduledVisit(dto.visitDate);
        ticketRepository.save(ticket);
    }
}
