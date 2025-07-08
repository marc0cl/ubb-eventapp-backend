package com.ubb.eventapp.service;

import com.ubb.eventapp.model.Friendship;
import com.ubb.eventapp.model.FriendshipId;

import java.util.Optional;

public interface FriendshipService {
    Friendship requestFriendship(Friendship friendship);
    Friendship acceptFriendship(FriendshipId id);
    Optional<Friendship> findById(FriendshipId id);
}
