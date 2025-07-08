package com.ubb.eventappbackend.service.impl;

import com.ubb.eventappbackend.model.Event;
import com.ubb.eventappbackend.model.ValidationState;
import com.ubb.eventappbackend.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

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
        Event event = Event.builder().id("1").estadoValidacion(ValidationState.PENDIENTE).build();
        when(eventRepository.findById("1")).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event result = service.approveEvent("1");

        assertEquals(ValidationState.APROBADO, result.getEstadoValidacion());
        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(captor.capture());
        assertEquals(ValidationState.APROBADO, captor.getValue().getEstadoValidacion());
    }
}
