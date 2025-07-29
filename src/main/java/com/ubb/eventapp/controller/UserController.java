package com.ubb.eventapp.controller;

import com.ubb.eventapp.model.*;
import com.ubb.eventapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PutMapping
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        return ResponseEntity.ok(service.updateProfile(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<ProfileSummary> getSummary(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getProfileSummary(id));
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<ProfileEvents> getProfileEvents(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getProfileEvents(id));
    }

    @GetMapping("/{id}/calendar")
    public ResponseEntity<List<CalendarEntry>> getCalendar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getEventCalendar(id));
    }

    @GetMapping("/{id}/to-attend")
    public ResponseEntity<EventsToAttend> eventsToAttend(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getEventsToAttend(id));
    }
}
