package com.ubb.eventappbackend.service.impl;

import com.ubb.eventappbackend.model.User;
import com.ubb.eventappbackend.model.ProfileSummary;
import com.ubb.eventappbackend.model.ProfileEvents;
import com.ubb.eventappbackend.model.CalendarEntry;
import com.ubb.eventappbackend.model.FriendshipState;
import com.ubb.eventappbackend.model.RegistrationState;
import com.ubb.eventappbackend.model.Trophy;
import com.ubb.eventappbackend.model.UserTrophy;
import com.ubb.eventappbackend.repository.UserRepository;
import com.ubb.eventappbackend.repository.FriendshipRepository;
import com.ubb.eventappbackend.repository.RegistrationRepository;
import com.ubb.eventappbackend.repository.EventRepository;
import com.ubb.eventappbackend.repository.UserTrophyRepository;
import com.ubb.eventappbackend.service.UserService;
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
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileSummary getProfileSummary(String userId) {
        long friendCount = friendshipRepository
                .findByUserIdAndEstado(userId, FriendshipState.ACEPTADA)
                .size();

        java.util.List<Trophy> trophies = userTrophyRepository
                .findByUser_Id(userId)
                .stream()
                .map(UserTrophy::getTrophy)
                .toList();

        return ProfileSummary.builder()
                .friendsCount(friendCount)
                .trophies(trophies)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileEvents getProfileEvents(String userId) {
        long eventsAttended = registrationRepository
                .findByUser_IdAndEstado(userId, RegistrationState.ASISTIO)
                .size();

        java.util.List<com.ubb.eventappbackend.model.Event> createdEvents =
                eventRepository.findByCreador_Id(userId);

        long eventsCreated = createdEvents.size();

        java.util.List<com.ubb.eventappbackend.model.Event> attendedEvents =
                registrationRepository.findByUser_IdAndEstado(userId, RegistrationState.ASISTIO)
                        .stream()
                        .map(registration -> registration.getEvent())
                        .toList();

        java.util.List<com.ubb.eventappbackend.model.Event> allEvents = new java.util.ArrayList<>();
        allEvents.addAll(createdEvents);
        allEvents.addAll(attendedEvents);

        java.util.Map<java.time.LocalDateTime, java.util.List<String>> grouped = new java.util.HashMap<>();
        for (com.ubb.eventappbackend.model.Event event : allEvents) {
            java.time.LocalDateTime date = event.getFechaInicio();
            grouped.computeIfAbsent(date, k -> new java.util.ArrayList<>()).add(event.getId());
        }

        java.util.List<CalendarEntry> calendar = grouped.entrySet().stream()
                .map(e -> CalendarEntry.builder()
                        .date(e.getKey())
                        .eventIds(e.getValue())
                        .build())
                .toList();

        return ProfileEvents.builder()
                .eventsAttended(eventsAttended)
                .eventsCreated(eventsCreated)
                .calendar(calendar)
                .build();
    }
}
