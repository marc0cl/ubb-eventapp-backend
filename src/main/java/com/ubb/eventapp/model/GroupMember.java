package com.ubb.eventapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grupo_miembro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMember {
    @EmbeddedId
    private GroupMemberId id;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "id_grupo")
    private Group group;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_usuario")
    private User user;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "rol_grupo", nullable = false)
    private GroupRole rolGrupo = GroupRole.USUARIO;
}
