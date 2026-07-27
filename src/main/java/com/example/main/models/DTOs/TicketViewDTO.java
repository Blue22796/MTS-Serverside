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
public class TicketViewDTO {

    private Long id;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String category;
    private String notes;
    private LocalDate proposedDate;
    private LocalDate scheduledDate;
    private Integer calls;
    private LocalDateTime creationDate;
}
