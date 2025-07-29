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
public class FriendshipId implements Serializable {
    @Column(name = "id_usr1")
    private Long user1Id;

    @Column(name = "id_usr2")
    private Long user2Id;
}
