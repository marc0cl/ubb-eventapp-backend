package com.ubb.eventappbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evento_revision")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventReview {
    @Id
    @Column(name = "id_evento", length = 36)
    private String idEvento;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_evento")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "id_mod")
    private User moderador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewState estado;

    @Column(length = 250)
    private String comentario;

    @Column(name = "rev_ts")
    private LocalDateTime revTs;
}
