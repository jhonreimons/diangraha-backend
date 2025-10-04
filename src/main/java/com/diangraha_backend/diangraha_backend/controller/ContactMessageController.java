package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.ContactMessageRequest;
import com.diangraha_backend.diangraha_backend.dto.ContactMessageResponse;
import com.diangraha_backend.diangraha_backend.service.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact-messages")
@RequiredArgsConstructor
public class ContactMessageController {
    
    private final ContactMessageService service;
    
    @PostMapping
    public ResponseEntity<ContactMessageResponse> create(@Valid @RequestBody ContactMessageRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
    
    @GetMapping
    public ResponseEntity<List<ContactMessageResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ContactMessageResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ContactMessageResponse> update(@PathVariable Long id, @Valid @RequestBody ContactMessageRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/dummy")
    public ResponseEntity<ContactMessageResponse> createDummy() {
        ContactMessageRequest dummy = new ContactMessageRequest();
        dummy.setFullName("John Doe");
        dummy.setEmail("john.doe@example.com");
        dummy.setPhoneNumber("+62812345678");
        dummy.setCompanyName("PT Example Corp");
        dummy.setInterestedIn("Web Development");
        dummy.setMessage("I am interested in your web development services. Please contact me for further discussion.");
        
        return ResponseEntity.ok(service.create(dummy));
    }
}