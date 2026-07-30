package com.example.main.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "omar_fadi_ticket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_seq_gen")
    @SequenceGenerator(name = "ticket_seq_gen", sequenceName = "ticket_seq", allocationSize = 1)
    private Long id;
    @Column(length = 100, nullable = false)
    private String customerName;
    @Column(length = 200)
    private String customerAddress;
    @Column(length = 50, nullable = false)
    private String customerPhone;
    @Column(length = 100)
    private String customerEmail;
    @Enumerated(EnumType.STRING)
    private TicketCategory category;
    @Column(length = 2000)
    private String notes;
    @Column(nullable = false)
    private LocalDate proposedDate;
    private LocalDateTime scheduledVisit;
    private Integer calls;
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TicketStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "technician_id", foreignKey = @ForeignKey(name = "FK_ticket_technician"))
    private Technician technician;
}
