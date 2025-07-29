package com.ubb.eventapp.controller;

import com.ubb.eventapp.model.Group;
import com.ubb.eventapp.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService service;

    @PostMapping
    public ResponseEntity<Group> create(@RequestBody Group group) {
        return ResponseEntity.ok(service.createGroup(group));
    }

    @PutMapping
    public ResponseEntity<Group> update(@RequestBody Group group) {
        return ResponseEntity.ok(service.updateGroup(group));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/representative/{userId}")
    public ResponseEntity<List<Group>> findByRepresentative(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.findByRepresentativeUser(userId));
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Group>> findByTags(@RequestParam("ids") Collection<Integer> ids) {
        return ResponseEntity.ok(service.findByTags(ids));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Group>> searchByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(service.findByName(name));
    }
}
