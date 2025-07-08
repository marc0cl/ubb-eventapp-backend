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
    @Column(name = "id_ticket", length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_reporter")
    private User reporter;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketState estado = TicketState.ABIERTO;
}
