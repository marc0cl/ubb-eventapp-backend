package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Friendship;
import com.ubb.eventappbackend.model.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {
}
