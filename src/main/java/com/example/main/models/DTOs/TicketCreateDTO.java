package com.example.main.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateDTO {
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String category;
    private String notes;
    private LocalDate proposedDate;
}
