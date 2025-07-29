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
    public ResponseEntity<Event> approve(@PathVariable Long id) {
        return ResponseEntity.ok(service.approveEvent(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<Event>> findByCreator(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findByCreator(userId));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Event>> findByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(service.findByGroup(groupId));
    }

    @GetMapping("/public")
    public ResponseEntity<List<Event>> findPublic() {
        return ResponseEntity.ok(service.findPublicEvents());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Event>> findPending() {
        return ResponseEntity.ok(service.findPendingEvents());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> findUpcoming(@RequestParam(required = false)
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                    LocalDateTime after) {
        return ResponseEntity.ok(service.findUpcomingEvents(after == null ? LocalDateTime.now() : after));
    }

    @GetMapping
    public ResponseEntity<List<Event>> findByIds(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(service.findByIds(ids));
    }
}
