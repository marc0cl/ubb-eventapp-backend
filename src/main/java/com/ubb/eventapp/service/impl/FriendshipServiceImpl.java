package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Friendship;
import com.ubb.eventapp.model.FriendshipId;
import com.ubb.eventapp.model.FriendshipState;
import com.ubb.eventapp.repository.FriendshipRepository;
import com.ubb.eventapp.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public Friendship requestFriendship(Friendship friendship) {
        if (friendship.getId() == null) {
            throw new IllegalArgumentException("Friendship id cannot be null");
        }

        if (friendship.getUser1() == null && friendship.getId().getUser1Id() != null) {
            friendship.setUser1(userRepository.getReferenceById(friendship.getId().getUser1Id()));
        }

        if (friendship.getUser2() == null && friendship.getId().getUser2Id() != null) {
            friendship.setUser2(userRepository.getReferenceById(friendship.getId().getUser2Id()));
        }

        return friendshipRepository.save(friendship);
    }

    @Override
    public Friendship acceptFriendship(FriendshipId id) {
        Optional<Friendship> friendshipOpt = friendshipRepository.findById(id);
        if (friendshipOpt.isEmpty()) {
            FriendshipId reversed = new FriendshipId(id.getUser2Id(), id.getUser1Id());
            friendshipOpt = friendshipRepository.findById(reversed);
        }
        Friendship friendship = friendshipOpt.orElseThrow();
        friendship.setEstado(FriendshipState.ACEPTADA);
        return friendshipRepository.save(friendship);
    }

    @Override
    public void rejectFriendship(FriendshipId id) {
        friendshipRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<User> findPendingFriendships(Long userId) {
        return friendshipRepository
                .findByUserIdAndEstado(userId, FriendshipState.PENDIENTE)
                .stream()
                .map(Friendship::getUser1)
                .toList();
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
