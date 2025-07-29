package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Event;
import com.ubb.eventapp.model.ValidationState;
import com.ubb.eventapp.repository.EventRepository;
import com.ubb.eventapp.service.EventService;
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
    public Event approveEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setEstadoValidacion(ValidationState.APROBADO);
        return eventRepository.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Event> findByCreator(Long userId) {
        return eventRepository.findByCreador_Id(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Event> findByGroup(Long groupId) {
        return eventRepository.findByGrupo_Id(groupId);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Event> findUpcomingEvents(java.time.LocalDateTime after) {
        return eventRepository.findByFechaInicioAfter(after);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Event> findByIds(java.util.List<Long> ids) {
        return ids == null || ids.isEmpty() ? java.util.Collections.emptyList() :
                eventRepository.findAllById(ids);
    }
}
