package com.example.main.controllers;

import com.example.main.models.dtos.TicketCreateDTO;
import com.example.main.models.dtos.TicketViewDTO;
import com.example.main.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TicketsController {

    private final TicketService ticketService;

    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/tickets")
    public List<TicketViewDTO> getTickets() {
        return ticketService.getTickets();
    }

    @PostMapping("/tickets/create")
    public TicketViewDTO createTicket(@RequestBody TicketCreateDTO dto) {
        return ticketService.createTicket(dto);
    }
}
