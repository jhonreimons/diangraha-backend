package com.diangraha_backend.diangraha_backend.service;

import com.diangraha_backend.diangraha_backend.dto.*;
import com.diangraha_backend.diangraha_backend.entity.ServiceEntity;
import com.diangraha_backend.diangraha_backend.entity.ServiceFeature;
import com.diangraha_backend.diangraha_backend.repository.ServiceFeatureRepository;
import com.diangraha_backend.diangraha_backend.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceFeatureRepository featureRepository;
    private final FileStorageService fileStorageService;

    // CREATE Service
    public ServiceResponse createService(ServiceRequest request) throws IOException {
        ServiceEntity service = new ServiceEntity();
        service.setName(request.getName());
        service.setShortDesc(request.getShortDesc());
        service.setLongDesc(request.getLongDesc());

        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            String imagePath = fileStorageService.storeFile(request.getImageFile(), "services");
            service.setImageUrl(imagePath);
        }

        ServiceEntity saved = serviceRepository.save(service);
        return mapToResponse(saved);
    }

    // ADD Feature
    public ServiceFeatureResponse addFeature(Long serviceId, ServiceFeatureRequest request) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        ServiceFeature feature = new ServiceFeature();
        feature.setFeatureName(request.getFeatureName());
        feature.setFeatureDesc(request.getFeatureDesc());
        feature.setService(service);

        ServiceFeature saved = featureRepository.save(feature);

        return new ServiceFeatureResponse(
                saved.getId(),
                saved.getFeatureName(),
                saved.getFeatureDesc()
        );
    }

    public List<ServiceResponse> getAll() {
        return serviceRepository.findAllWithFeatures()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    // GET BY ID
    public ServiceResponse getById(Long id) {
        ServiceEntity service = serviceRepository.findByIdWithFeatures(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        return mapToResponse(service);
    }

    // UPDATE Service
    public ServiceResponse updateService(Long id, ServiceRequest request) throws IOException {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setName(request.getName());
        service.setShortDesc(request.getShortDesc());
        service.setLongDesc(request.getLongDesc());

        MultipartFile imageFile = request.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = fileStorageService.storeFile(imageFile, "services");
            service.setImageUrl(imagePath);
        }

        ServiceEntity updated = serviceRepository.save(service);
        return mapToResponse(updated);
    }

    // DELETE Service
    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("Service not found");
        }
        serviceRepository.deleteById(id);
    }

    // DELETE Feature
    public void deleteFeature(Long serviceId, Long featureId) {
        ServiceFeature feature = featureRepository.findById(featureId)
                .orElseThrow(() -> new RuntimeException("Feature not found"));
        
        if (!feature.getService().getId().equals(serviceId)) {
            throw new RuntimeException("Feature does not belong to this service");
        }
        
        featureRepository.deleteById(featureId);
    }

    private ServiceResponse mapToResponse(ServiceEntity service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getShortDesc(),
                service.getLongDesc(),
                service.getImageUrl(),
                service.getFeatures() != null ?
                        service.getFeatures().stream()
                                .map(f -> new ServiceFeatureResponse(f.getId(), f.getFeatureName(), f.getFeatureDesc()))
                                .collect(Collectors.toList())
                        : List.of()
        );
    }
}
