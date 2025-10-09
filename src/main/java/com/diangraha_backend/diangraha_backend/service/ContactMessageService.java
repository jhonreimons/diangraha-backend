package com.diangraha_backend.diangraha_backend.service;

import com.diangraha_backend.diangraha_backend.dto.ContactMessageRequest;
import com.diangraha_backend.diangraha_backend.dto.ContactMessageResponse;
import com.diangraha_backend.diangraha_backend.entity.ContactMessage;
import com.diangraha_backend.diangraha_backend.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
    private final ContactMessageRepository repository;

    // CREATE (Public)
    public ContactMessageResponse create(ContactMessageRequest request) {
        ContactMessage cm = new ContactMessage();
        cm.setFullName(request.getFullName());
        cm.setEmail(request.getEmail());
        cm.setPhoneNumber(request.getPhoneNumber());
        cm.setCompanyName(request.getCompanyName());
        cm.setInterestedIn(request.getInterestedIn());
        cm.setMessage(request.getMessage());

        return mapToResponse(repository.save(cm));
    }
    public ContactMessageResponse getById(Long id) {
        ContactMessage cm = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        return mapToResponse(cm);
    }
    // READ all (Admin only)
    public List<ContactMessageResponse> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // UPDATE (Admin only)
    public ContactMessageResponse update(Long id, ContactMessageRequest request) {
        ContactMessage cm = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        cm.setFullName(request.getFullName());
        cm.setEmail(request.getEmail());
        cm.setPhoneNumber(request.getPhoneNumber());
        cm.setCompanyName(request.getCompanyName());
        cm.setInterestedIn(request.getInterestedIn());
        cm.setMessage(request.getMessage());

        return mapToResponse(repository.save(cm));
    }

    // DELETE (Admin only)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ContactMessageResponse mapToResponse(ContactMessage cm) {
        return ContactMessageResponse.builder()
                .id(cm.getId())
                .fullName(cm.getFullName())
                .email(cm.getEmail())
                .phoneNumber(cm.getPhoneNumber())
                .companyName(cm.getCompanyName())
                .interestedIn(cm.getInterestedIn())
                .message(cm.getMessage())
                .createdAt(cm.getCreatedAt())
                .build();
    }
}
