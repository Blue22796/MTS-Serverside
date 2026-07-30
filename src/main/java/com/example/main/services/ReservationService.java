package com.example.main.services;

import com.example.main.models.Ticket;
import com.example.main.models.TicketStatus;
import com.example.main.repositories.TicketRepository;
import com.example.main.repositories.TechnicianRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {

    private final TicketRepository ticketRepository;
    private final TechnicianRepository technicianRepository;


    private static final List<LocalTime> SLOTS = Arrays.asList(
            LocalTime.of(8, 0),
            LocalTime.of(10, 0),
            LocalTime.of(12, 0),
            LocalTime.of(14, 0),
            LocalTime.of(16, 0),
            LocalTime.of(18, 0)
    );
    public LocalDateTime getFirstAvailableSlot(){
        List<LocalDateTime> slots = getAvailableSlots(new Long(-1));
	    if(slots.isEmpty())return null;
        return slots.get(0);
    }
    public List<LocalDateTime> getAvailableSlots(Long id) {

        int techniciansCount = id == -1? (int) technicianRepository.count() : 1;
        List<Ticket> scheduledTickets;
        if(id == -1)
                scheduledTickets = ticketRepository.findByTechnicianIsNotNullAndScheduledVisitIsNotNull();
        else scheduledTickets = ticketRepository.findByTechnicianIdAndScheduledVisitIsNotNull(id);
        Map<LocalDateTime, Long> reservations = scheduledTickets.stream()
                .collect(Collectors.groupingBy(
                        Ticket::getScheduledVisit,
                        Collectors.counting()
                ));

        LocalDate today = LocalDate.now();
        List<LocalDateTime> availableSlots = new ArrayList<LocalDateTime>();
        for (int day = 0; day < 14; day++) {
            LocalDate date = today.plusDays(day);
            for (LocalTime slot : SLOTS) {
                LocalDateTime visit = LocalDateTime.of(
                        date,
                        slot
                );
                long booked = reservations.getOrDefault(
                        visit,
                        0L
                );
                if (booked < techniciansCount && !slot.isBefore(LocalTime.now())) {
                    availableSlots.add(visit);
                }
            }
        }
        return availableSlots;
    }
    boolean technicianIsAvailable(Long technicianId, LocalDateTime time ){

        if (!SLOTS.contains(time.toLocalTime())) {
            System.out.println("Invalid time slot: " + time.toLocalTime());
            return false;
        }
        Ticket reservedTicket = ticketRepository.findByTechnicianIdAndScheduledVisitAndStatus(technicianId, time, TicketStatus.IN_PROGRESS)
                .stream().findAny().orElse(null);
        if(reservedTicket != null)
            System.out.println("Reserved ticket for technician " + technicianId + " at " + time + ": " + time.toLocalTime() + " Ticket with id" + reservedTicket.getId());
        return reservedTicket == null;
    }

    public List<LocalDateTime> getAvailableSlotsForTicket(Long id) {
        Ticket ticket = ticketRepository.getReferenceById(id);
        if(ticket == null){
            throw new IllegalArgumentException("Ticket does not exist");
        }
        if(ticket.getTechnician() == null){
            throw new IllegalArgumentException("Ticket has not been assigned");
        }
        Long techId = ticket.getTechnician().getId();
        return getAvailableSlots(techId);
    }
}
