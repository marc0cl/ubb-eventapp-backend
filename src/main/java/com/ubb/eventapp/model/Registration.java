package com.ubb.eventapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inscripcion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registration {
    @EmbeddedId
    private RegistrationId id;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "id_evento")
    private Event event;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_usuario")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationState estado = RegistrationState.INSCRITO;
}
