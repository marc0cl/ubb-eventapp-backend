package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TrophyRepository extends JpaRepository<Trophy, Integer> {

    /**
     * Retrieves trophies with the specified identifiers.
     *
     * @param ids collection of trophy ids
     * @return list of trophies matching the ids
     */
    List<Trophy> findByIdIn(Collection<Integer> ids);
}
