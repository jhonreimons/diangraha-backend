package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.*;
import com.diangraha_backend.diangraha_backend.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Create new service with image upload")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ServiceResponse> createService(
            @Parameter(description = "Service name") @RequestParam("name") String name,
            @Parameter(description = "Short description") @RequestParam("shortDesc") String shortDesc,
            @Parameter(description = "Long description") @RequestParam("longDesc") String longDesc,
            @Parameter(description = "Service image file") @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
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


    @Operation(summary = "Update service with optional image upload")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ServiceResponse> updateService(
            @Parameter(description = "Service ID") @PathVariable Long id,
            @Parameter(description = "Service name") @RequestParam("name") String name,
            @Parameter(description = "Short description") @RequestParam("shortDesc") String shortDesc,
            @Parameter(description = "Long description") @RequestParam("longDesc") String longDesc,
            @Parameter(description = "Service image file (optional)") @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        ServiceRequest request = new ServiceRequest(name, shortDesc, longDesc, imageFile);
        return ResponseEntity.ok(serviceService.updateService(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
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
