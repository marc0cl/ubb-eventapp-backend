package com.ubb.eventappbackend.service.impl;

import com.ubb.eventappbackend.model.*;
import com.ubb.eventappbackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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

        LocalDateTime now = LocalDateTime.now();
        Event createdEvent = Event.builder().id("e1").fechaInicio(now).build();
        when(eventRepository.findByCreador_Id("u1")).thenReturn(List.of(createdEvent));
        Registration attended = Registration.builder()
                .event(Event.builder().id("e2").fechaInicio(now).build())
                .build();
        when(registrationRepository.findByUser_IdAndEstado("u1", RegistrationState.ASISTIO))
                .thenReturn(List.of(attended));
        Registration toAttend = Registration.builder()
                .event(Event.builder().id("e3").fechaInicio(now).build())
                .build();
        when(registrationRepository.findByUser_IdAndEstado("u1", RegistrationState.INSCRITO))
                .thenReturn(List.of(toAttend));

        ProfileSummary summary = service.getProfileSummary("u1");

        assertEquals("john", summary.getUsername());
        assertEquals(2, summary.getFriendsCount());
        assertEquals(List.of(trophy), summary.getTrophies());
        assertNotNull(summary.getEvents());
        assertEquals(1, summary.getEvents().getEventsCreated());
        assertEquals(1, summary.getEvents().getEventsAttended());
        assertEquals(1, summary.getEvents().getEventsToAttend());
    }

    @Test
    void getProfileEvents_buildsEventSummaryFromRepositories() {
        LocalDateTime now = LocalDateTime.now();
        when(eventRepository.findByCreador_Id("u1"))
                .thenReturn(List.of(Event.builder().id("e1").fechaInicio(now).build()));
        Registration attended = Registration.builder()
                .event(Event.builder().id("e2").fechaInicio(now).build())
                .build();
        when(registrationRepository.findByUser_IdAndEstado("u1", RegistrationState.ASISTIO))
                .thenReturn(List.of(attended));
        Registration toAttend = Registration.builder()
                .event(Event.builder().id("e3").fechaInicio(now).build())
                .build();
        when(registrationRepository.findByUser_IdAndEstado("u1", RegistrationState.INSCRITO))
                .thenReturn(List.of(toAttend));

        ProfileEvents result = service.getProfileEvents("u1");

        assertEquals(1, result.getEventsCreated());
        assertEquals(1, result.getEventsAttended());
        assertEquals(1, result.getEventsToAttend());

        List<CalendarEntry> calendar = service.getEventCalendar("u1");

        assertEquals(1, calendar.size());
        assertTrue(calendar.get(0).getEventIds().contains("e1"));
        assertTrue(calendar.get(0).getEventIds().contains("e2"));
        assertTrue(calendar.get(0).getEventIds().contains("e3"));
    }

    @Test
    void getEventsToAttend_returnsIdsOfUpcomingEvents() {
        Registration toAttend1 = Registration.builder()
                .event(Event.builder().id("e1").build())
                .build();
        Registration toAttend2 = Registration.builder()
                .event(Event.builder().id("e2").build())
                .build();
        when(registrationRepository.findByUser_IdAndEstado("u1", RegistrationState.INSCRITO))
                .thenReturn(List.of(toAttend1, toAttend2));

        EventsToAttend result = service.getEventsToAttend("u1");
        List<String> ids = result.getEventIds();

        assertEquals(List.of("e1", "e2"), ids);
    }
}
