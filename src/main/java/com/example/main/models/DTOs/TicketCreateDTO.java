package com.example.main.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateDTO {
    public String customerName;
    public String customerAddress;
    public String customerPhone;
    public String customerEmail;
    public String category;
    public String notes;
    public LocalDate proposedDate;
}
