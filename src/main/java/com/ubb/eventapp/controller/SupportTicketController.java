package com.ubb.eventapp.controller;

import com.ubb.eventapp.model.SupportTicket;
import com.ubb.eventapp.service.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class SupportTicketController {

    private final SupportTicketService service;

    @GetMapping
    public ResponseEntity<java.util.List<SupportTicket>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<SupportTicket> open(@RequestBody SupportTicket ticket) {
        return ResponseEntity.ok(service.openTicket(ticket));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<SupportTicket> close(@PathVariable Long id) {
        return ResponseEntity.ok(service.closeTicket(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportTicket> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
