package com.ubb.eventapp.service;

import com.ubb.eventapp.model.Trophy;

import java.util.List;
import java.util.Optional;

public interface TrophyService {
    Optional<Trophy> findById(Integer id);

    /**
     * Retrieves trophies matching the provided ids.
     *
     * @param ids list of trophy ids
     * @return list of trophies
     */
    List<Trophy> findByIds(List<Integer> ids);
}
