package com.example.main.services;

import com.example.main.models.Technician;
import com.example.main.models.dtos.TechnicianCreateDTO;
import com.example.main.models.dtos.TechnicianViewDTO;
import com.example.main.repositories.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnicianService {

    private final TechnicianRepository technicianRepository;
    private final TechnicianMapper technicianMapper;

    public TechnicianViewDTO createTechnician(TechnicianCreateDTO dto) {
        Technician technician = technicianMapper.createTechnician(dto);
        technician = technicianRepository.save(technician);
        return technicianMapper.technicianToView(technician);
    }

    public List<TechnicianViewDTO> getTechnicians() {
        return technicianRepository.findAll()
                .stream()
                .map(technicianMapper::technicianToView)
                .collect(Collectors.toList());
    }
}
