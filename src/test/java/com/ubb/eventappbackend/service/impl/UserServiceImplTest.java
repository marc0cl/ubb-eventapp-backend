package com.ubb.eventappbackend.service.impl;

import com.ubb.eventappbackend.model.*;
import com.ubb.eventappbackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserRepository userRepository;
    private FriendshipRepository friendshipRepository;
    private RegistrationRepository registrationRepository;
    private EventRepository eventRepository;
    private UserTrophyRepository userTrophyRepository;
    private UserServiceImpl service;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        friendshipRepository = mock(FriendshipRepository.class);
        registrationRepository = mock(RegistrationRepository.class);
        eventRepository = mock(EventRepository.class);
        userTrophyRepository = mock(UserTrophyRepository.class);
        service = new UserServiceImpl(userRepository, friendshipRepository, registrationRepository, eventRepository, userTrophyRepository);
    }

    @Test
    void getProfileSummary_buildsSummaryFromRepositories() {
        User user = User.builder().id("u1").username("john").build();
        when(userRepository.findById("u1")).thenReturn(Optional.of(user));
        when(friendshipRepository.findByUserIdAndEstado("u1", FriendshipState.ACEPTADA))
                .thenReturn(List.of(new Friendship(), new Friendship()));
        Trophy trophy = Trophy.builder().id(1).nombre("T1").build();
        UserTrophy ut = UserTrophy.builder().trophy(trophy).build();
        when(userTrophyRepository.findByUser_Id("u1")).thenReturn(List.of(ut));

        ProfileSummary summary = service.getProfileSummary("u1");

        assertEquals("john", summary.getUsername());
        assertEquals(2, summary.getFriendsCount());
        assertEquals(List.of(trophy), summary.getTrophies());
    }
}
