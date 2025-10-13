package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.BrandRequest;
import com.diangraha_backend.diangraha_backend.dto.BrandResponse;
import com.diangraha_backend.diangraha_backend.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandResponse>> findAll() {
        return ResponseEntity.ok(brandService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.findById(id));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<BrandResponse> create(
            @RequestParam("name") String name,
            @RequestParam(value = "logoFile") MultipartFile logoFile
    ) throws IOException {
        BrandRequest request = new BrandRequest();
        request.setName(name);
        return ResponseEntity.ok(brandService.create(request, logoFile));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<BrandResponse> update(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "logoFile", required = false) MultipartFile logoFile
    ) throws IOException {
        BrandRequest request = new BrandRequest();
        request.setName(name);
        return ResponseEntity.ok(brandService.update(id, request, logoFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
