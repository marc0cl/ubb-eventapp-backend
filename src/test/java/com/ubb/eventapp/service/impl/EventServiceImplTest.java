package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Event;
import com.ubb.eventapp.model.ValidationState;
import com.ubb.eventapp.model.Visibility;
import com.ubb.eventapp.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceImplTest {

    private EventRepository eventRepository;
    private EventServiceImpl service;

    @BeforeEach
    void setup() {
        eventRepository = mock(EventRepository.class);
        service = new EventServiceImpl(eventRepository);
    }

    @Test
    void approveEvent_setsStateToApprovedAndSaves() {
        Event event = Event.builder().id(1L).estadoValidacion(ValidationState.PENDIENTE).build();
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event result = service.approveEvent(1L);

        assertEquals(ValidationState.APROBADO, result.getEstadoValidacion());
        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(captor.capture());
        assertEquals(ValidationState.APROBADO, captor.getValue().getEstadoValidacion());
    }

    @Test
    void findByIds_delegatesToRepository() {
        when(eventRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(new Event(), new Event()));

        service.findByIds(List.of(1L, 2L));

        verify(eventRepository).findAllById(List.of(1L, 2L));
    }

    @Test
    void findPublicEvents_delegatesToRepository() {
        service.findPublicEvents();

        verify(eventRepository).findByVisibilidad(Visibility.PUBLICO);
    }
}
