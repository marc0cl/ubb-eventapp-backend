package com.ubb.eventapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_soporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_reporter")
    private User reporter;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketState estado = TicketState.ABIERTO;
}
