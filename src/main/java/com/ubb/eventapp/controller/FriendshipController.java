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

    @PostMapping("/{user1Id}/{user2Id}")
    public ResponseEntity<Friendship> requestByIds(@PathVariable Long user1Id,
                                                   @PathVariable Long user2Id) {
        Friendship friendship = Friendship.builder()
                .id(new FriendshipId(user1Id, user2Id))
                .build();
        return ResponseEntity.ok(service.requestFriendship(friendship));
    }

    @PostMapping("/accept")
    public ResponseEntity<Friendship> accept(@RequestBody FriendshipId id) {
        return ResponseEntity.ok(service.acceptFriendship(id));
    }

    @PostMapping("/reject")
    public ResponseEntity<Friendship> reject(@RequestBody FriendshipId id) {
        return ResponseEntity.ok(service.rejectFriendship(id));
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<java.util.List<com.ubb.eventapp.model.User>> pending(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findPendingFriendships(userId));
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<java.util.List<com.ubb.eventapp.model.User>> friends(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findFriends(userId));
    }

    @GetMapping("/{user1}/{user2}")
    public ResponseEntity<Friendship> findById(@PathVariable Long user1, @PathVariable Long user2) {
        FriendshipId id = new FriendshipId(user1, user2);
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{user1}/{user2}")
    public ResponseEntity<Void> delete(@PathVariable Long user1, @PathVariable Long user2) {
        FriendshipId id = new FriendshipId(user1, user2);
        service.deleteFriendship(id);
        return ResponseEntity.ok().build();
    }
}
