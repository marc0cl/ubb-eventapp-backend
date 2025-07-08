package com.ubb.eventappbackend.controller;

import com.ubb.eventappbackend.model.Registration;
import com.ubb.eventappbackend.model.RegistrationId;
import com.ubb.eventappbackend.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService service;

    @PostMapping
    public ResponseEntity<Registration> register(@RequestBody Registration registration) {
        return ResponseEntity.ok(service.register(registration));
    }

    @PostMapping("/cancel")
    public ResponseEntity<Registration> cancel(@RequestBody RegistrationId id) {
        return ResponseEntity.ok(service.cancelRegistration(id));
    }

    @GetMapping("/{eventId}/{userId}")
    public ResponseEntity<Registration> findById(@PathVariable String eventId, @PathVariable String userId) {
        RegistrationId id = new RegistrationId(eventId, userId);
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
