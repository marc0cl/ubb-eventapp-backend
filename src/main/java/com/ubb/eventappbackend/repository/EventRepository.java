package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
