package com.example.main.services;

import com.example.main.models.Ticket;
import com.example.main.repositories.TicketRepository;
import com.example.main.repositories.TechnicianRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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


    public LocalDateTime getFirstAvailableSlot() {

        int techniciansCount = (int) technicianRepository.count();

        List<Ticket> scheduledTickets =
                ticketRepository.findByTechnicianIsNotNullAndScheduledVisitIsNotNull();


        Map<LocalDateTime, Long> reservations = scheduledTickets.stream()
                .collect(Collectors.groupingBy(
                        Ticket::getScheduledVisit,
                        Collectors.counting()
                ));


        LocalDate today = LocalDate.now();

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

                if (booked < techniciansCount) {
                    return visit;
                }
            }
        }

        return null;
    }
}
