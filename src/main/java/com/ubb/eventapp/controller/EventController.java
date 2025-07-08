package com.ubb.eventapp.controller;

import com.ubb.eventapp.model.Event;
import com.ubb.eventapp.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        return ResponseEntity.ok(service.createEvent(event));
    }

    @PutMapping
    public ResponseEntity<Event> update(@RequestBody Event event) {
        return ResponseEntity.ok(service.updateEvent(event));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Event> approve(@PathVariable String id) {
        return ResponseEntity.ok(service.approveEvent(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<Event>> findByCreator(@PathVariable String userId) {
        return ResponseEntity.ok(service.findByCreator(userId));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Event>> findByGroup(@PathVariable String groupId) {
        return ResponseEntity.ok(service.findByGroup(groupId));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> findUpcoming(@RequestParam(required = false)
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                    LocalDateTime after) {
        return ResponseEntity.ok(service.findUpcomingEvents(after == null ? LocalDateTime.now() : after));
    }

    @GetMapping
    public ResponseEntity<List<Event>> findByIds(@RequestParam("ids") List<String> ids) {
        return ResponseEntity.ok(service.findByIds(ids));
    }
}
