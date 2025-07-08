package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.UserTrophy;
import com.ubb.eventappbackend.model.UserTrophyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTrophyRepository extends JpaRepository<UserTrophy, UserTrophyId> {
}
