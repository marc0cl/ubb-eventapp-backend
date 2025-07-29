package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Friendship;
import com.ubb.eventapp.model.FriendshipId;
import com.ubb.eventapp.model.FriendshipState;
import com.ubb.eventapp.repository.FriendshipRepository;
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
        FriendshipId id = new FriendshipId(1L, 2L);
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
