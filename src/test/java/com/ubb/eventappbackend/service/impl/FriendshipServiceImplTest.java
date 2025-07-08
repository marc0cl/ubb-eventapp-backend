package com.ubb.eventappbackend.service.impl;

import com.ubb.eventappbackend.model.Friendship;
import com.ubb.eventappbackend.model.FriendshipId;
import com.ubb.eventappbackend.model.FriendshipState;
import com.ubb.eventappbackend.repository.FriendshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FriendshipServiceImplTest {

    private FriendshipRepository friendshipRepository;
    private FriendshipServiceImpl service;

    @BeforeEach
    void setup() {
        friendshipRepository = mock(FriendshipRepository.class);
        service = new FriendshipServiceImpl(friendshipRepository);
    }

    @Test
    void acceptFriendship_setsStateToAcceptedAndSaves() {
        FriendshipId id = new FriendshipId("u1", "u2");
        Friendship friendship = Friendship.builder().id(id).estado(FriendshipState.PENDIENTE).build();
        when(friendshipRepository.findById(id)).thenReturn(Optional.of(friendship));
        when(friendshipRepository.save(any(Friendship.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Friendship result = service.acceptFriendship(id);

        assertEquals(FriendshipState.ACEPTADA, result.getEstado());
        ArgumentCaptor<Friendship> captor = ArgumentCaptor.forClass(Friendship.class);
        verify(friendshipRepository).save(captor.capture());
        assertEquals(FriendshipState.ACEPTADA, captor.getValue().getEstado());
    }
}
