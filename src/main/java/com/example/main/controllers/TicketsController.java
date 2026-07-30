package com.example.main.controllers;

import com.example.main.models.dtos.TicketAssignDTO;
import com.example.main.models.dtos.TicketCreateDTO;
import com.example.main.models.dtos.TicketViewDTO;
import com.example.main.services.ResponseMapper;
import com.example.main.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.main.models.dtos.TicketRescheduleDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketsController {

    private final TicketService ticketService;
    private final ResponseMapper responseMapper;
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ticketService.getTicket(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getTickets() {
        try {
            return ResponseEntity.ok(ticketService.getTickets());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createTicket(
            @RequestBody TicketCreateDTO dto) {

        try {
            TicketViewDTO ticket =
                    ticketService.createTicket(dto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ticket);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/assign")
    public ResponseEntity<?> assignTicket(
            @RequestBody TicketAssignDTO dto) {

        try {
            ticketService.assignTicket(dto);
            Map<String, String> response = responseMapper.messageToResponse("Assignment succeeded");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/re-assign")
    public ResponseEntity<?> reassignTicket(
            @RequestBody TicketAssignDTO dto) {

        try {
            ticketService.reassignTicket(dto);
            Map<String, String> response = responseMapper.messageToResponse("Re-assignment succeeded");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
    @PutMapping("cancel/{id}")
    public ResponseEntity<?> cancelTicket(@PathVariable("id") Long id){
        try{
            ticketService.cancelTicket(id);
            Map<String, String> response = responseMapper.messageToResponse("Ticket cancelled");
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/reschedule")
    public ResponseEntity<?> reschedule(
            @RequestBody TicketRescheduleDTO dto) {
        try {
            ticketService.rescheduleTicket(dto);
            Map<String, String> response = responseMapper.messageToResponse("Rescheduled successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}