package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.AchievementRequest;
import com.diangraha_backend.diangraha_backend.dto.AchievementResponse;
import com.diangraha_backend.diangraha_backend.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService service;

    // GET all
    @GetMapping
    public List<AchievementResponse> getAllAchievements() {
        return service.getAllAchievements();
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<AchievementResponse> getAchievementById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // GET limited
    @GetMapping("/limit/{count}")
    public List<AchievementResponse> getLimitedAchievements(@PathVariable int count) {
        return service.getLimitedAchievements(count);
    }

    // CREATE with image upload
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<AchievementResponse> create(
            @RequestParam("title") String title,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        AchievementRequest request = new AchievementRequest();
        request.setTitle(title);
        return ResponseEntity.ok(service.create(request, imageFile));
    }

    // UPDATE with image upload
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<AchievementResponse> update(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        AchievementRequest request = new AchievementRequest();
        request.setTitle(title);
        return ResponseEntity.ok(service.update(id, request, imageFile));
    }

    @DeleteMapping("/{id}")
    public void deleteAchievement(@PathVariable Long id) {
        service.deleteAchievement(id);
    }
}

