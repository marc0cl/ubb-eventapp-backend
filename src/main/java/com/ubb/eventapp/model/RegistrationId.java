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
    @Column(name = "id_evento", length = 36)
    private String eventId;

    @Column(name = "id_usuario", length = 36)
    private String userId;
}
