package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.Event;
import com.ubb.eventapp.model.EventSourceType;
import com.ubb.eventapp.model.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

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

    /**
     * Finds events created by the specified user.
     *
     * @param userId identifier of the creator
     * @return list of events created by the user
     */
    List<Event> findByCreador_Id(Long userId);

    /**
     * Retrieves all events belonging to the given group.
     *
     * @param groupId identifier of the group
     * @return list of events for that group
     */
    List<Event> findByGrupo_Id(Long groupId);

    /**
     * Fetches events that start after the provided date.
     *
     * @param date starting date-time (exclusive)
     * @return list of upcoming events
     */
    List<Event> findByFechaInicioAfter(LocalDateTime date);
}
