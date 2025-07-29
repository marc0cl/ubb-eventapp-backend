package com.ubb.eventapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RegistrationId implements Serializable {
    @Column(name = "id_evento")
    private Long eventId;

    @Column(name = "id_usuario")
    private Long userId;
}
