package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Event;
import com.ubb.eventappbackend.model.EventSourceType;
import com.ubb.eventappbackend.model.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {

    /**
     * Retrieves events that start within the specified date range.
     *
     * @param start start date-time of the range (inclusive)
     * @param end   end date-time of the range (inclusive)
     * @return list of events in the given range
     */
    List<Event> findByFechaInicioBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Retrieves events with the given visibility.
     *
     * @param visibilidad visibility to match
     * @return list of events matching the visibility
     */
    List<Event> findByVisibilidad(Visibility visibilidad);

    /**
     * Retrieves events whose origin matches the provided type.
     *
     * @param origenTipo origin type of the event
     * @return list of events originating from the given source type
     */
    List<Event> findByOrigenTipo(EventSourceType origenTipo);
}
