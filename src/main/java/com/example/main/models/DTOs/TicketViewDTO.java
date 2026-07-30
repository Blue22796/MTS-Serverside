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

    public Long id;
    public String customerName;
    public String customerAddress;
    public String customerPhone;
    public String category;
    public String notes;
    public LocalDate proposedDate;
    public LocalDateTime scheduledDate;
    public Integer calls;
    public LocalDateTime creationDate;
    public String technicianName;
    public String status;
}
