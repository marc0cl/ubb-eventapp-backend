package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Friendship;
import com.ubb.eventapp.model.FriendshipId;
import com.ubb.eventapp.model.FriendshipState;
import com.ubb.eventapp.repository.FriendshipRepository;
import com.ubb.eventapp.model.User;
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

    @Test
    void rejectFriendship_setsStateToBlockedAndSaves() {
        FriendshipId id = new FriendshipId(1L, 2L);
        Friendship friendship = Friendship.builder().id(id).estado(FriendshipState.PENDIENTE).build();
        when(friendshipRepository.findById(id)).thenReturn(Optional.of(friendship));
        when(friendshipRepository.save(any(Friendship.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Friendship result = service.rejectFriendship(id);

        assertEquals(FriendshipState.BLOQUEADA, result.getEstado());
        ArgumentCaptor<Friendship> captor = ArgumentCaptor.forClass(Friendship.class);
        verify(friendshipRepository).save(captor.capture());
        assertEquals(FriendshipState.BLOQUEADA, captor.getValue().getEstado());
    }

    @Test
    void findFriends_returnsOtherUsersInAcceptedFriendships() {
        Long userId = 1L;
        Friendship f1 = Friendship.builder()
                .user1(User.builder().id(userId).build())
                .user2(User.builder().id(2L).build())
                .estado(FriendshipState.ACEPTADA)
                .build();
        Friendship f2 = Friendship.builder()
                .user1(User.builder().id(3L).build())
                .user2(User.builder().id(userId).build())
                .estado(FriendshipState.ACEPTADA)
                .build();
        when(friendshipRepository.findByUserIdAndEstado(userId, FriendshipState.ACEPTADA))
                .thenReturn(java.util.List.of(f1, f2));

        java.util.List<User> result = service.findFriends(userId);

        assertEquals(java.util.List.of(f1.getUser2(), f2.getUser1()), result);
    }

    @Test
    void deleteFriendship_delegatesToRepository() {
        FriendshipId id = new FriendshipId(1L, 2L);

        service.deleteFriendship(id);

        verify(friendshipRepository).deleteById(id);
    }
}
