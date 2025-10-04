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
    
    public ContactMessageResponse create(ContactMessageRequest request) {
        ContactMessage message = new ContactMessage();
        message.setFullName(request.getFullName());
        message.setEmail(request.getEmail());
        message.setPhoneNumber(request.getPhoneNumber());
        message.setCompanyName(request.getCompanyName());
        message.setInterestedIn(request.getInterestedIn());
        message.setMessage(request.getMessage());
        
        ContactMessage saved = repository.save(message);
        return mapToResponse(saved);
    }
    
    public List<ContactMessageResponse> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public ContactMessageResponse getById(Long id) {
        ContactMessage message = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact message not found"));
        return mapToResponse(message);
    }
    
    public ContactMessageResponse update(Long id, ContactMessageRequest request) {
        ContactMessage message = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact message not found"));
        
        message.setFullName(request.getFullName());
        message.setEmail(request.getEmail());
        message.setPhoneNumber(request.getPhoneNumber());
        message.setCompanyName(request.getCompanyName());
        message.setInterestedIn(request.getInterestedIn());
        message.setMessage(request.getMessage());
        
        ContactMessage updated = repository.save(message);
        return mapToResponse(updated);
    }
    
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Contact message not found");
        }
        repository.deleteById(id);
    }
    
    private ContactMessageResponse mapToResponse(ContactMessage message) {
        return new ContactMessageResponse(
                message.getId(),
                message.getFullName(),
                message.getEmail(),
                message.getPhoneNumber(),
                message.getCompanyName(),
                message.getInterestedIn(),
                message.getMessage(),
                message.getCreatedAt()
        );
    }
}