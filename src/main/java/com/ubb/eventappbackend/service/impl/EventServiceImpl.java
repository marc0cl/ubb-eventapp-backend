package com.ubb.eventappbackend.service.impl;

import com.ubb.eventappbackend.model.Event;
import com.ubb.eventappbackend.model.ValidationState;
import com.ubb.eventappbackend.repository.EventRepository;
import com.ubb.eventappbackend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event approveEvent(String eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setEstadoValidacion(ValidationState.APROBADO);
        return eventRepository.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Event> findById(String id) {
        return eventRepository.findById(id);
    }
}
