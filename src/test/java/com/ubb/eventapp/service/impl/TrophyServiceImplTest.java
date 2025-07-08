package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Trophy;
import com.ubb.eventapp.repository.TrophyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrophyServiceImplTest {

    private TrophyRepository trophyRepository;
    private TrophyServiceImpl service;

    @BeforeEach
    void setup() {
        trophyRepository = mock(TrophyRepository.class);
        service = new TrophyServiceImpl(trophyRepository);
    }

    @Test
    void findById_delegatesToRepository() {
        when(trophyRepository.findById(1)).thenReturn(Optional.of(new Trophy()));

        Optional<Trophy> result = service.findById(1);

        assertTrue(result.isPresent());
        verify(trophyRepository).findById(1);
    }

    @Test
    void findByIds_usesRepository() {
        when(trophyRepository.findByIdIn(List.of(1, 2)))
                .thenReturn(List.of(new Trophy(), new Trophy()));

        List<Trophy> result = service.findByIds(List.of(1, 2));

        assertEquals(2, result.size());
        ArgumentCaptor<List<Integer>> captor = ArgumentCaptor.forClass(List.class);
        verify(trophyRepository).findByIdIn(captor.capture());
        assertEquals(List.of(1, 2), captor.getValue());
    }
}
