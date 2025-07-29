package com.ubb.eventapp.service;

import com.ubb.eventapp.model.Event;

import java.util.Optional;

public interface EventService {
    Event createEvent(Event event);
    Event updateEvent(Event event);
    Event approveEvent(Integer eventId);
    Optional<Event> findById(Integer id);

    java.util.List<Event> findByCreator(Integer userId);
    java.util.List<Event> findByGroup(Integer groupId);
    java.util.List<Event> findUpcomingEvents(java.time.LocalDateTime after);

    /**
     * Retrieves the events with the provided identifiers.
     *
     * @param ids list of event ids
     * @return list of events found
     */
    java.util.List<Event> findByIds(java.util.List<Integer> ids);
}
