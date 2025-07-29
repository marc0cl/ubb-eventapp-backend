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
public class UserTrophyId implements Serializable {
    @Column(name = "id_usuario")
    private Long userId;

    @Column(name = "id_trofeo")
    private Integer trophyId;
}
