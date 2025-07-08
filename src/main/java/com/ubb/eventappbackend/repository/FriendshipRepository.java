package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Friendship;
import com.ubb.eventappbackend.model.FriendshipId;
import com.ubb.eventappbackend.model.FriendshipState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {

    /**
     * Returns friendships of a user matching the given state. The user can be
     * either in the {@code user1} or {@code user2} position of the friendship.
     *
     * @param userId identifier of the user
     * @param estado desired friendship state
     * @return list of friendships for the user in the specified state
     */
    @Query("select f from Friendship f where (f.user1.id = :userId or f.user2.id = :userId) and f.estado = :estado")
    List<Friendship> findByUserIdAndEstado(@Param("userId") String userId, @Param("estado") FriendshipState estado);
}
