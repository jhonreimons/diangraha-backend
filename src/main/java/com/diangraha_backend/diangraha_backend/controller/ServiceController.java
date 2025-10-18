package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.*;
import com.diangraha_backend.diangraha_backend.service.ServiceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/services")


public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ServiceResponse> createService(
            @RequestParam("name") String name,
            @RequestParam("shortDesc") String shortDesc,
            @RequestParam("longDesc") String longDesc,
            @RequestParam(value = "imageFile") MultipartFile imageFile
    ) throws IOException {
        ServiceRequest request = new ServiceRequest(name, shortDesc, longDesc, imageFile);
        return ResponseEntity.ok(serviceService.createService(request));
    }


    @PostMapping("/{id}/features")
    public ResponseEntity<ServiceFeatureResponse> addFeature(
            @PathVariable Long id,
            @RequestBody ServiceFeatureRequest request
    ) {
        return ResponseEntity.ok(serviceService.addFeature(id, request));
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAll() {
        return ResponseEntity.ok(serviceService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getById(id));
    }


    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ServiceResponse> updateService(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("shortDesc") String shortDesc,
            @RequestParam("longDesc") String longDesc,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        ServiceRequest request = new ServiceRequest(name, shortDesc, longDesc, imageFile);
        return ResponseEntity.ok(serviceService.updateService(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{serviceId}/features/{featureId}")
    public ResponseEntity<ServiceFeatureResponse> updateFeature(
            @PathVariable Long serviceId,
            @PathVariable Long featureId,
            @RequestBody ServiceFeatureRequest request
    ) {
        return ResponseEntity.ok(serviceService.updateFeature(serviceId, featureId, request));
    }

    @DeleteMapping("/{serviceId}/features/{featureId}")
    public ResponseEntity<Void> deleteFeature(
            @PathVariable Long serviceId,
            @PathVariable Long featureId
    ) {
        serviceService.deleteFeature(serviceId, featureId);
        return ResponseEntity.noContent().build();
    }
}
