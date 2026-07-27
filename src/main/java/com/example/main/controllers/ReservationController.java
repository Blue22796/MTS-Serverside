package com.example.main.controllers;

import com.example.main.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/next-slot")
    public ResponseEntity<LocalDateTime> getFirstAvailableSlot() {

        LocalDateTime slot = reservationService.getFirstAvailableSlot();

        if (slot == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(slot);
    }
}
