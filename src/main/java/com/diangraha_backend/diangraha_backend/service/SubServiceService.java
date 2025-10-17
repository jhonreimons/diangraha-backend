package com.diangraha_backend.diangraha_backend.service;

import com.diangraha_backend.diangraha_backend.entity.*;
import com.diangraha_backend.diangraha_backend.repository.*;
import com.diangraha_backend.diangraha_backend.dto.SubServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubServiceService {

    private final SubServiceRepository subServiceRepository;
    private final ServiceRepository serviceRepository;

    public List<SubService> getAll(Long serviceId) {
        return subServiceRepository.findByServiceId(serviceId);
    }

    public SubService create(Long serviceId, SubServiceRequest request) {
        ServiceEntity serviceEntity = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        SubService subService = SubService.builder()
                .name(request.getName())
                .description(request.getDescription())
                .service(serviceEntity)
                .build();
        return subServiceRepository.save(subService);
    }

    public SubService update(Long serviceId, Long id, SubServiceRequest request) {
        SubService existing = subServiceRepository.findById(id)
                .filter(s -> s.getService().getId().equals(serviceId))
                .orElseThrow(() -> new RuntimeException("SubService not found"));
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        return subServiceRepository.save(existing);
    }

    public void delete(Long serviceId, Long id) {
        SubService existing = subServiceRepository.findById(id)
                .filter(s -> s.getService().getId().equals(serviceId))
                .orElseThrow(() -> new RuntimeException("SubService not found"));
        subServiceRepository.delete(existing);
    }
}
