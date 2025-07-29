package com.ubb.eventapp.service;

import com.ubb.eventapp.model.Event;

import java.util.Optional;

public interface EventService {
    Event createEvent(Event event);
    Event updateEvent(Event event);
    Event approveEvent(Long eventId);
    Optional<Event> findById(Long id);

    java.util.List<Event> findByCreator(Long userId);
    java.util.List<Event> findByGroup(Long groupId);
    java.util.List<Event> findUpcomingEvents(java.time.LocalDateTime after);

    /**
     * Retrieves the events with the provided identifiers.
     *
     * @param ids list of event ids
     * @return list of events found
     */
    java.util.List<Event> findByIds(java.util.List<Long> ids);
}
