package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.SubServiceRequest;
import com.diangraha_backend.diangraha_backend.dto.SubServiceResponse;
import com.diangraha_backend.diangraha_backend.entity.SubService;
import com.diangraha_backend.diangraha_backend.service.SubServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services/{serviceId}/sub-services")
@RequiredArgsConstructor
public class SubServiceController {

    private final SubServiceService subServiceService;

    @GetMapping
    public ResponseEntity<List<SubServiceResponse>> getAll(@PathVariable Long serviceId) {
        List<SubServiceResponse> responses = subServiceService.getAll(serviceId).stream()
                .map(s -> SubServiceResponse.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .description(s.getDescription())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<SubServiceResponse> create(
            @PathVariable Long serviceId,
            @RequestBody SubServiceRequest request
    ) {
        SubService subService = subServiceService.create(serviceId, request);
        return ResponseEntity.ok(SubServiceResponse.builder()
                .id(subService.getId())
                .name(subService.getName())
                .description(subService.getDescription())
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubServiceResponse> update(
            @PathVariable Long serviceId,
            @PathVariable Long id,
            @RequestBody SubServiceRequest request
    ) {
        SubService subService = subServiceService.update(serviceId, id, request);
        return ResponseEntity.ok(SubServiceResponse.builder()
                .id(subService.getId())
                .name(subService.getName())
                .description(subService.getDescription())
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long serviceId, @PathVariable Long id) {
        subServiceService.delete(serviceId, id);
        return ResponseEntity.noContent().build();
    }
}
