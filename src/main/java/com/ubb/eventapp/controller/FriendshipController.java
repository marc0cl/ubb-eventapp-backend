package com.ubb.eventapp.controller;

import com.ubb.eventapp.model.Friendship;
import com.ubb.eventapp.model.FriendshipId;
import com.ubb.eventapp.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendships")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService service;

    @PostMapping
    public ResponseEntity<Friendship> request(@RequestBody Friendship friendship) {
        return ResponseEntity.ok(service.requestFriendship(friendship));
    }

    @PostMapping("/accept")
    public ResponseEntity<Friendship> accept(@RequestBody FriendshipId id) {
        return ResponseEntity.ok(service.acceptFriendship(id));
    }

    @GetMapping("/{user1}/{user2}")
    public ResponseEntity<Friendship> findById(@PathVariable String user1, @PathVariable String user2) {
        FriendshipId id = new FriendshipId(user1, user2);
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
