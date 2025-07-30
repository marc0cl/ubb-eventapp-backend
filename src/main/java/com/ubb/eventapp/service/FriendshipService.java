package com.ubb.eventapp.service;

import com.ubb.eventapp.model.Friendship;
import com.ubb.eventapp.model.FriendshipId;
import java.util.List;

import java.util.Optional;
import com.ubb.eventapp.model.User;

public interface FriendshipService {
    Friendship requestFriendship(Friendship friendship);
    Friendship acceptFriendship(FriendshipId id);
    Friendship rejectFriendship(FriendshipId id);
    /**
     * Lists the users that have a pending friendship relation with the given
     * user.
     *
     * @param userId identifier of the user
     * @return users awaiting this user's response
     */
    java.util.List<User> findPendingFriendships(Long userId);
    Optional<Friendship> findById(FriendshipId id);
    List<User> findFriends(Long userId);
    void deleteFriendship(FriendshipId id);
}
