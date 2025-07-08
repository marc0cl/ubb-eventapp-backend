package com.ubb.eventappbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_trofeo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTrophy {
    @EmbeddedId
    private UserTrophyId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_usuario")
    private User user;

    @ManyToOne
    @MapsId("trophyId")
    @JoinColumn(name = "id_trofeo")
    private Trophy trophy;

    @Column(name = "obt_en")
    private LocalDateTime obtainedAt;
}
