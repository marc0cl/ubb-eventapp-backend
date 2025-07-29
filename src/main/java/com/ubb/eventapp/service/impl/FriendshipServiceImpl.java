package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Friendship;
import com.ubb.eventapp.model.FriendshipId;
import com.ubb.eventapp.model.FriendshipState;
import com.ubb.eventapp.repository.FriendshipRepository;
import com.ubb.eventapp.service.FriendshipService;
import com.ubb.eventapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;

    @Override
    public Friendship requestFriendship(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }

    @Override
    public Friendship acceptFriendship(FriendshipId id) {
        Friendship friendship = friendshipRepository.findById(id).orElseThrow();
        friendship.setEstado(FriendshipState.ACEPTADA);
        return friendshipRepository.save(friendship);
    }

    @Override
    public Friendship rejectFriendship(FriendshipId id) {
        Friendship friendship = friendshipRepository.findById(id).orElseThrow();
        friendship.setEstado(FriendshipState.BLOQUEADA);
        return friendshipRepository.save(friendship);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Friendship> findPendingFriendships(Long userId) {
        return friendshipRepository.findByUserIdAndEstado(userId, FriendshipState.PENDIENTE);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Friendship> findById(FriendshipId id) {
        return friendshipRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<User> findFriends(Long userId) {
        return friendshipRepository
                .findByUserIdAndEstado(userId, FriendshipState.ACEPTADA)
                .stream()
                .map(f -> f.getUser1().getId().equals(userId) ? f.getUser2() : f.getUser1())
                .toList();
    }

    @Override
    public void deleteFriendship(FriendshipId id) {
        friendshipRepository.deleteById(id);
    }
}
