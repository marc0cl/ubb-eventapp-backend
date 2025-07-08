package com.ubb.eventapp.controller;

import com.ubb.eventapp.model.Trophy;
import com.ubb.eventapp.service.TrophyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trophies")
@RequiredArgsConstructor
public class TrophyController {

    private final TrophyService service;

    @GetMapping("/{id}")
    public ResponseEntity<Trophy> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Trophy>> findByIds(@RequestParam("ids") List<Integer> ids) {
        return ResponseEntity.ok(service.findByIds(ids));
    }
}
