package com.ubb.eventappbackend.model;

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
public class FriendshipId implements Serializable {
    @Column(name = "id_usr1", length = 36)
    private String user1Id;

    @Column(name = "id_usr2", length = 36)
    private String user2Id;
}
