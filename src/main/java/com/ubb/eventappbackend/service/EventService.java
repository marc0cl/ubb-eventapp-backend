package com.ubb.eventappbackend.service;

import com.ubb.eventappbackend.model.Event;

import java.util.Optional;

public interface EventService {
    Event createEvent(Event event);
    Event updateEvent(Event event);
    Event approveEvent(String eventId);
    Optional<Event> findById(String id);

    java.util.List<Event> findByCreator(String userId);
    java.util.List<Event> findByGroup(String groupId);
    java.util.List<Event> findUpcomingEvents(java.time.LocalDateTime after);

    /**
     * Retrieves the events with the provided identifiers.
     *
     * @param ids list of event ids
     * @return list of events found
     */
    java.util.List<Event> findByIds(java.util.List<String> ids);
}
