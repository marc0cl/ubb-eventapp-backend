package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.UserTrophy;
import com.ubb.eventappbackend.model.UserTrophyId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTrophyRepository extends JpaRepository<UserTrophy, UserTrophyId> {
    /**
     * Retrieves trophies obtained by the given user.
     *
     * @param userId identifier of the user
     * @return list of user trophies
     */
    List<UserTrophy> findByUser_Id(String userId);
}
