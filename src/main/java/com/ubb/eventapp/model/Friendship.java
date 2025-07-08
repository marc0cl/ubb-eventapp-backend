package com.ubb.eventapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "amistad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship {
    @EmbeddedId
    private FriendshipId id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipState estado = FriendshipState.PENDIENTE;

    @ManyToOne
    @MapsId("user1Id")
    @JoinColumn(name = "id_usr1")
    private User user1;

    @ManyToOne
    @MapsId("user2Id")
    @JoinColumn(name = "id_usr2")
    private User user2;
}
