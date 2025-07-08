package com.ubb.eventappbackend.service.impl;

import com.ubb.eventappbackend.model.ProfileSummary;
import com.ubb.eventappbackend.model.Trophy;
import com.ubb.eventappbackend.model.User;
import com.ubb.eventappbackend.model.FriendshipState;
import com.ubb.eventappbackend.model.RegistrationState;
import com.ubb.eventappbackend.repository.UserRepository;
import com.ubb.eventappbackend.repository.FriendshipRepository;
import com.ubb.eventappbackend.repository.RegistrationRepository;
import com.ubb.eventappbackend.repository.EventRepository;
import com.ubb.eventappbackend.repository.UserTrophyRepository;
import com.ubb.eventappbackend.service.UserService;
import java.util.List;
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

        long attendedEvents = registrationRepository
                .findByUser_IdAndEstado(userId, RegistrationState.ASISTIO)
                .size();

        long createdEvents = eventRepository.countByCreador_Id(userId);

        List<Trophy> trophies = userTrophyRepository.findByUser_Id(userId)
                .stream()
                .map(ut -> ut.getTrophy())
                .toList();

        return ProfileSummary.builder()
                .friendCount(friendCount)
                .attendedEvents(attendedEvents)
                .createdEvents(createdEvents)
                .trophies(trophies)
                .build();
    }
}
