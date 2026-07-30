package com.example.main.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "omar_fadi_technician")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tech_seq_gen")
    @SequenceGenerator(name = "tech_seq_gen", sequenceName = "technician_seq", allocationSize = 1)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String location;

    @Column(length = 50, nullable = false)
    private String phoneNumber;
}
