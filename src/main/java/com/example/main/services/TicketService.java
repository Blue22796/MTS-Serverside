package com.example.main.services;

import com.example.main.models.Ticket;
import com.example.main.models.dtos.TicketCreateDTO;
import com.example.main.models.dtos.TicketViewDTO;
import com.example.main.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public TicketViewDTO createTicket(TicketCreateDTO dto) {
        Ticket ticket = ticketMapper.createTicket(dto);
        ticket = ticketRepository.save(ticket);
        return ticketMapper.ticketToView(ticket);
    }

    public List<TicketViewDTO> getTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::ticketToView)
                .collect(Collectors.toList());
    }
}
