package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.AchievementRequest;
import com.diangraha_backend.diangraha_backend.dto.AchievementResponse;
import com.diangraha_backend.diangraha_backend.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@RequiredArgsConstructor
@Slf4j // log.error
public class AchievementController {

    private final AchievementService service;

    // GET all
    @GetMapping
    public List<AchievementResponse> getAllAchievements() {
        log.info("Fetching all achievements");
        return service.getAllAchievements();
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<AchievementResponse> getAchievementById(@PathVariable Long id) {
        log.info("Fetching achievement by id={}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    // GET limited
    @GetMapping("/limit/{count}")
    public List<AchievementResponse> getLimitedAchievements(@PathVariable int count) {
        log.info("Fetching {} achievements", count);
        return service.getLimitedAchievements(count);
    }

    // CREATE with image upload
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<AchievementResponse> create(
            @RequestParam("title") String title,
            @RequestParam(value = "imageFile") MultipartFile imageFile
    ) throws IOException {
        log.info("Creating achievement with title={}", title);
        AchievementRequest request = AchievementRequest.builder()
                .title(title)
                .build();
        return ResponseEntity.ok(service.create(request, imageFile));
    }

    // UPDATE with image upload
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<AchievementResponse> update(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        AchievementRequest request = AchievementRequest.builder()
                .title(title)
                .build();
        return ResponseEntity.ok(service.update(id, request, imageFile));
    }

    @DeleteMapping("/{id}")
    public void deleteAchievement(@PathVariable Long id) {
        log.warn("Deleting achievement id={}", id);
        service.deleteAchievement(id);
    }
}
