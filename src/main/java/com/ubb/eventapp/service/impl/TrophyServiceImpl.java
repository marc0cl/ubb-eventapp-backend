package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Trophy;
import com.ubb.eventapp.repository.TrophyRepository;
import com.ubb.eventapp.service.TrophyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TrophyServiceImpl implements TrophyService {

    private final TrophyRepository trophyRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Trophy> findById(Integer id) {
        return trophyRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trophy> findByIds(List<Integer> ids) {
        return ids == null || ids.isEmpty()
                ? List.of()
                : trophyRepository.findByIdIn(ids);
    }
}
