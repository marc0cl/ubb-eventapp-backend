package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.*;
import com.ubb.eventapp.repository.*;
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
        User user = User.builder().id(1L).username("john").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(friendshipRepository.findByUserIdAndEstado(1L, FriendshipState.ACEPTADA))
                .thenReturn(List.of(new Friendship(), new Friendship()));
        Trophy trophy = Trophy.builder().id(1).nombre("T1").build();
        UserTrophy ut = UserTrophy.builder().trophy(trophy).build();
        when(userTrophyRepository.findByUser_Id(1L)).thenReturn(List.of(ut));

        LocalDateTime now = LocalDateTime.now();
        Event createdEvent = Event.builder().id(1L).fechaInicio(now).build();
        when(eventRepository.findByCreador_Id(1L)).thenReturn(List.of(createdEvent));
        Registration attended = Registration.builder()
                .event(Event.builder().id(2L).fechaInicio(now).build())
                .build();
        when(registrationRepository.findByUser_IdAndEstado(1L, RegistrationState.ASISTIO))
                .thenReturn(List.of(attended));
        Registration toAttend = Registration.builder()
                .event(Event.builder().id(3L).fechaInicio(now).build())
                .build();
        when(registrationRepository.findByUser_IdAndEstado(1L, RegistrationState.INSCRITO))
                .thenReturn(List.of(toAttend));

        ProfileSummary summary = service.getProfileSummary(1L);

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
        when(eventRepository.findByCreador_Id(1L))
                .thenReturn(List.of(Event.builder().id(1L).fechaInicio(now).build()));
        Registration attended = Registration.builder()
                .event(Event.builder().id(2L).fechaInicio(now).build())
                .build();
        when(registrationRepository.findByUser_IdAndEstado(1L, RegistrationState.ASISTIO))
                .thenReturn(List.of(attended));
        Registration toAttend = Registration.builder()
                .event(Event.builder().id(3L).fechaInicio(now).build())
                .build();
        when(registrationRepository.findByUser_IdAndEstado(1L, RegistrationState.INSCRITO))
                .thenReturn(List.of(toAttend));
        when(eventRepository.findAllById(List.of(3L)))
                .thenReturn(List.of(toAttend.getEvent()));

        ProfileEvents result = service.getProfileEvents(1L);

        assertEquals(1, result.getEventsCreated());
        assertEquals(1, result.getEventsAttended());
        assertEquals(1, result.getEventsToAttend());

        List<CalendarEntry> calendar = service.getEventCalendar(1L);

        assertEquals(1, calendar.size());
        assertTrue(calendar.get(0).getEventIds().contains(1L));
        assertTrue(calendar.get(0).getEventIds().contains(2L));
        assertTrue(calendar.get(0).getEventIds().contains(3L));
    }

    @Test
    void getEventsToAttend_returnsIdsOfUpcomingEvents() {
        Registration toAttend1 = Registration.builder()
                .event(Event.builder().id(1L).build())
                .build();
        Registration toAttend2 = Registration.builder()
                .event(Event.builder().id(2L).build())
                .build();
        when(registrationRepository.findByUser_IdAndEstado(1L, RegistrationState.INSCRITO))
                .thenReturn(List.of(toAttend1, toAttend2));

        EventsToAttend result = service.getEventsToAttend(1L);
        List<Long> ids = result.getEventIds();

        assertEquals(List.of(1L, 2L), ids);
    }
}
