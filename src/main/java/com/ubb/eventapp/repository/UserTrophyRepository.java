package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.UserTrophy;
import com.ubb.eventapp.model.UserTrophyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTrophyRepository extends JpaRepository<UserTrophy, UserTrophyId> {
    /**
     * Retrieves trophies obtained by the given user.
     *
     * @param userId id of the user
     * @return list of user trophies
     */
    java.util.List<UserTrophy> findByUser_Id(Long userId);
}
