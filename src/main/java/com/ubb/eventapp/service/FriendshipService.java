package com.ubb.eventapp.service;

import com.ubb.eventapp.model.Friendship;
import com.ubb.eventapp.model.FriendshipId;
import java.util.List;

import java.util.Optional;

public interface FriendshipService {
    Friendship requestFriendship(Friendship friendship);
    Friendship acceptFriendship(FriendshipId id);
    Friendship rejectFriendship(FriendshipId id);
    List<Friendship> findPendingFriendships(Long userId);
    Optional<Friendship> findById(FriendshipId id);
}
