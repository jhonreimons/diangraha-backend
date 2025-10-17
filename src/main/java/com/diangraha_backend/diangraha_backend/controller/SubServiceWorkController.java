package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.SubServiceWorkRequest;
import com.diangraha_backend.diangraha_backend.dto.SubServiceWorkResponse;
import com.diangraha_backend.diangraha_backend.entity.SubServiceWork;
import com.diangraha_backend.diangraha_backend.service.SubServiceWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sub-services/{subServiceId}/works")
@RequiredArgsConstructor
public class SubServiceWorkController {

    private final SubServiceWorkService subServiceWorkService;

    @GetMapping
    public ResponseEntity<List<SubServiceWorkResponse>> getAll(@PathVariable Long subServiceId) {
        List<SubServiceWorkResponse> responses = subServiceWorkService.getAll(subServiceId).stream()
                .map(w -> SubServiceWorkResponse.builder()
                        .id(w.getId())
                        .description(w.getDescription())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<SubServiceWorkResponse> create(
            @PathVariable Long subServiceId,
            @RequestBody SubServiceWorkRequest request
    ) {
        SubServiceWork created = subServiceWorkService.create(subServiceId, request);
        SubServiceWorkResponse response = SubServiceWorkResponse.builder()
                .id(created.getId())
                .description(created.getDescription())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubServiceWorkResponse> update(
            @PathVariable Long subServiceId,
            @PathVariable Long id,
            @RequestBody SubServiceWorkRequest request
    ) {
        SubServiceWork updated = subServiceWorkService.update(subServiceId, id, request);
        SubServiceWorkResponse response = SubServiceWorkResponse.builder()
                .id(updated.getId())
                .description(updated.getDescription())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long subServiceId, @PathVariable Long id) {
        subServiceWorkService.delete(subServiceId, id);
        return ResponseEntity.noContent().build();
    }
}
