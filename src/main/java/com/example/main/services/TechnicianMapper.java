package com.example.main.services;

import com.example.main.models.Technician;
import com.example.main.models.dtos.TechnicianCreateDTO;
import com.example.main.models.dtos.TechnicianViewDTO;
import org.springframework.stereotype.Component;

@Component
public class TechnicianMapper {

    public Technician createTechnician(TechnicianCreateDTO dto) {
        return Technician.builder()
                .name(dto.name)
                .location(dto.location)
                .phoneNumber(dto.phoneNumber)
                .build();
    }

    public TechnicianViewDTO technicianToView(Technician technician) {
        return TechnicianViewDTO.builder()
                .id(technician.getId())
                .name(technician.getName())
                .location(technician.getLocation())
                .phoneNumber(technician.getPhoneNumber())
                .build();
    }
}
