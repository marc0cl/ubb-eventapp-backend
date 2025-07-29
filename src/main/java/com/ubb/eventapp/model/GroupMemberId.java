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
public class GroupMemberId implements Serializable {
    @Column(name = "id_grupo")
    private Long groupId;

    @Column(name = "id_usuario")
    private Long userId;
}
