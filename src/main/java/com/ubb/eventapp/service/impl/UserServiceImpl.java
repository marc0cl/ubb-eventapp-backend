package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.User;
import com.ubb.eventapp.model.ProfileSummary;
import com.ubb.eventapp.model.ProfileEvents;
import com.ubb.eventapp.model.CalendarEntry;
import com.ubb.eventapp.model.EventsToAttend;
import com.ubb.eventapp.model.FriendshipState;
import com.ubb.eventapp.model.RegistrationState;
import com.ubb.eventapp.model.Trophy;
import com.ubb.eventapp.model.UserTrophy;
import com.ubb.eventapp.repository.UserRepository;
import com.ubb.eventapp.repository.FriendshipRepository;
import com.ubb.eventapp.repository.RegistrationRepository;
import com.ubb.eventapp.repository.EventRepository;
import com.ubb.eventapp.repository.UserTrophyRepository;
import com.ubb.eventapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final UserTrophyRepository userTrophyRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateProfile(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    /**
     * Builds a profile overview for the specified user.
     * <p>
     * Event related metrics are gathered through {@link #getProfileEvents(String)}
     * to keep the logic centralized.
     * </p>
     */
    public ProfileSummary getProfileSummary(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        long friendCount = friendshipRepository
                .findByUserIdAndEstado(userId, FriendshipState.ACEPTADA)
                .size();

        java.util.List<Trophy> trophies = userTrophyRepository
                .findByUser_Id(userId)
                .stream()
                .map(UserTrophy::getTrophy)
                .toList();

        ProfileEvents events = getProfileEvents(userId);

        return ProfileSummary.builder()
                .username(user.getUsername())
                .friendsCount(friendCount)
                .trophies(trophies)
                .events(events)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileEvents getProfileEvents(Integer userId) {
        java.util.List<com.ubb.eventapp.model.Registration> attendedRegs =
                registrationRepository.findByUser_IdAndEstado(userId, RegistrationState.ASISTIO);
        long eventsAttended = attendedRegs.size();

        long eventsCreated = eventRepository.findByCreador_Id(userId).size();

        long eventsToAttend = getEventsToAttend(userId).getEventIds().size();

        return ProfileEvents.builder()
                .eventsAttended(eventsAttended)
                .eventsCreated(eventsCreated)
                .eventsToAttend(eventsToAttend)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<CalendarEntry> getEventCalendar(Integer userId) {
        java.util.List<com.ubb.eventapp.model.Event> createdEvents =
                eventRepository.findByCreador_Id(userId);

        java.util.List<com.ubb.eventapp.model.Event> attendedEvents =
                registrationRepository.findByUser_IdAndEstado(userId, RegistrationState.ASISTIO)
                        .stream()
                        .map(com.ubb.eventapp.model.Registration::getEvent)
                        .toList();

        java.util.List<Integer> toAttendIds = getEventsToAttend(userId).getEventIds();
        java.util.List<com.ubb.eventapp.model.Event> toAttendEvents =
                eventRepository.findAllById(toAttendIds);

        java.util.List<com.ubb.eventapp.model.Event> allEvents = new java.util.ArrayList<>();
        allEvents.addAll(createdEvents);
        allEvents.addAll(attendedEvents);
        allEvents.addAll(toAttendEvents);

        java.util.Map<java.time.LocalDateTime, java.util.List<Integer>> grouped = new java.util.HashMap<>();
        for (com.ubb.eventapp.model.Event event : allEvents) {
            java.time.LocalDateTime date = event.getFechaInicio();
            grouped.computeIfAbsent(date, k -> new java.util.ArrayList<>()).add(event.getId());
        }

        return grouped.entrySet().stream()
                .map(e -> CalendarEntry.builder()
                        .date(e.getKey())
                        .eventIds(e.getValue())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EventsToAttend getEventsToAttend(Integer userId) {
        java.util.List<Integer> ids = registrationRepository
                .findByUser_IdAndEstado(userId, RegistrationState.INSCRITO)
                .stream()
                .map(r -> r.getEvent().getId())
                .toList();
        return EventsToAttend.builder()
                .eventIds(ids)
                .build();
    }
}
