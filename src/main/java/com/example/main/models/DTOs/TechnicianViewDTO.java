package com.example.main.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianViewDTO {
    public Long id;
    public String name;
    public String location;
    public String phoneNumber;
}
