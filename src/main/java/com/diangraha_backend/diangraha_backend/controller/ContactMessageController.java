package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.BrandResponse;
import com.diangraha_backend.diangraha_backend.dto.ContactMessageRequest;
import com.diangraha_backend.diangraha_backend.dto.ContactMessageResponse;
import com.diangraha_backend.diangraha_backend.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact-messages")
@RequiredArgsConstructor
public class ContactMessageController {
    private final ContactMessageService service;

    // PUBLIC (POST only)
    @PostMapping
    public ResponseEntity<ContactMessageResponse> create(@RequestBody ContactMessageRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
    // PROTECTED (login required)
    @GetMapping
    public List<ContactMessageResponse> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ContactMessageResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ContactMessageResponse> update(@PathVariable Long id,
                                                         @RequestBody ContactMessageRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
