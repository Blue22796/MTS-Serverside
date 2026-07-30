package com.example.main.controllers;

import com.example.main.models.dtos.TechnicianCreateDTO;
import com.example.main.models.dtos.TechnicianViewDTO;
import com.example.main.services.TechnicianService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/technicians")
@AllArgsConstructor
public class TechnicianController {

    private final TechnicianService technicianService;

    @GetMapping
    public ResponseEntity<?> getTechnicians() {
        try {
            return ResponseEntity.ok(
                    technicianService.getTechnicians()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createTechnician(
            @RequestBody TechnicianCreateDTO dto) {

        try {
            TechnicianViewDTO technician =
                    technicianService.createTechnician(dto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(technician);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}