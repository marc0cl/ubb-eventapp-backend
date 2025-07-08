package com.ubb.eventappbackend.service;

import com.ubb.eventappbackend.model.Friendship;
import com.ubb.eventappbackend.model.FriendshipId;

import java.util.Optional;

public interface FriendshipService {
    Friendship requestFriendship(Friendship friendship);
    Friendship acceptFriendship(FriendshipId id);
    Optional<Friendship> findById(FriendshipId id);
}
