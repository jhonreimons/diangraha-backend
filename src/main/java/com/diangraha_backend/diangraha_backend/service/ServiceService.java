package com.diangraha_backend.diangraha_backend.service;

import com.diangraha_backend.diangraha_backend.dto.*;
import com.diangraha_backend.diangraha_backend.entity.ServiceEntity;
import com.diangraha_backend.diangraha_backend.entity.ServiceFeature;
import com.diangraha_backend.diangraha_backend.entity.SubService;
import com.diangraha_backend.diangraha_backend.entity.SubServiceWork;
import com.diangraha_backend.diangraha_backend.repository.ServiceFeatureRepository;
import com.diangraha_backend.diangraha_backend.repository.ServiceRepository;
import com.diangraha_backend.diangraha_backend.repository.SubServiceRepository;
import com.diangraha_backend.diangraha_backend.repository.SubServiceWorkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceFeatureRepository featureRepository;
    private final SubServiceRepository subServiceRepository;
    private final SubServiceWorkRepository subServiceWorkRepository;
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

    public SubServiceResponse addSubService(Long serviceId, SubServiceRequest request) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        SubService subService = new SubService();
        subService.setName(request.getName());
        subService.setDescription(request.getDescription());
        subService.setService(service);

        SubService saved = subServiceRepository.save(subService);

        // Mapping dari entity Work ke DTO SubServiceWorkResponse
        List<SubServiceWorkResponse> workResponses = saved.getWorks() != null
                ? saved.getWorks().stream()
                .map(work -> new SubServiceWorkResponse(
                        work.getId(),
                        work.getDescription()
                ))
                .toList()
                : List.of();

        return new SubServiceResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                workResponses
        );
    }

    public SubServiceWorkResponse addSubServiceWork(Long subServiceId, SubServiceWorkRequest request){
        SubService subService = subServiceRepository.findById(subServiceId)
                .orElseThrow(() -> new RuntimeException("Work of Sub Services not found"));
        SubServiceWork subServiceWork = new SubServiceWork();
        subServiceWork.setDescription(request.getDescription());
        subServiceWork.setSubService(subService);

        SubServiceWork save = subServiceWorkRepository.save(subServiceWork);

        return  new SubServiceWorkResponse(
                save.getId(),
                save.getDescription()
        );
    }

    @Transactional
    public List<ServiceResponse> getAllServices() {
        List<ServiceEntity> services = serviceRepository.findAllWithFeatures();
        List<ServiceEntity> servicesWithSubs = serviceRepository.findAllWithSubServices();
        List<SubService> subServicesWithWorks = serviceRepository.findAllSubServicesWithWorks();
        Map<Long, List<SubService>> subServiceMap = subServicesWithWorks.stream()
                .collect(Collectors.groupingBy(SubService::getId));

        Map<Long, ServiceEntity> serviceMap = new HashMap<>();
        for (ServiceEntity s : services) {
            serviceMap.put(s.getId(), s);
        }

        for (ServiceEntity s : servicesWithSubs) {
            ServiceEntity existing = serviceMap.get(s.getId());
            if (existing != null) {
                existing.setSubServices(s.getSubServices());
                if (existing.getSubServices() != null) {
                    for (SubService sub : existing.getSubServices()) {
                        List<SubService> subs = subServiceMap.get(sub.getId());
                        if (subs != null && !subs.isEmpty()) {
                            sub.setWorks(subs.get(0).getWorks());
                        }
                    }
                }
            }
        }


        return serviceMap.values().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    // GET BY ID
    public ServiceResponse getById(Long id) {
        ServiceEntity service = serviceRepository.findByIdWithDetails(id)
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

    // UPDATE Feature
    public ServiceFeatureResponse updateFeature(Long serviceId, Long featureId, ServiceFeatureRequest request) {
        ServiceFeature feature = featureRepository.findById(featureId)
                .orElseThrow(() -> new RuntimeException("Feature not found"));

        if (!feature.getService().getId().equals(serviceId)) {
            throw new RuntimeException("Feature does not belong to this service");
        }

        feature.setFeatureName(request.getFeatureName());
        feature.setFeatureDesc(request.getFeatureDesc());

        ServiceFeature updated = featureRepository.save(feature);

        return new ServiceFeatureResponse(
                updated.getId(),
                updated.getFeatureName(),
                updated.getFeatureDesc()
        );
    }


    public SubServiceResponse updateSubService(Long serviceId, Long subServiceId, SubServiceRequest request) {
        // Cari sub-service berdasarkan ID
        SubService subService = subServiceRepository.findById(subServiceId)
                .orElseThrow(() -> new RuntimeException("Sub Service Not Found"));

        // Pastikan sub-service memang milik service yang sesuai
        if (!subService.getService().getId().equals(serviceId)) {
            throw new RuntimeException("Sub Service does not belong to this service");
        }

        // Update field dasar
        subService.setName(request.getName());
        subService.setDescription(request.getDescription());

        // Simpan perubahan
        SubService updated = subServiceRepository.save(subService);

        // Mapping dari entity Work ke DTO SubServiceWorkResponse
        List<SubServiceWorkResponse> workResponses = updated.getWorks() != null
                ? updated.getWorks().stream()
                .map(work -> new SubServiceWorkResponse(
                        work.getId(),
                        work.getDescription()
                ))
                .toList()
                : List.of();

        // Return DTO response
        return new SubServiceResponse(
                updated.getId(),
                updated.getName(),
                updated.getDescription(),
                workResponses
        );
    }

    // Update service
    public  SubServiceWorkResponse updateSubServiceWork ( Long subServiceId, Long subServiceWorkId, SubServiceWorkRequest request){

        SubServiceWork subServiceWork = subServiceWorkRepository.findById(subServiceWorkId)
                .orElseThrow(() -> new RuntimeException("Sub Service not found"));

        if (!subServiceWork.getSubService().getId().equals(subServiceId)) {
            throw new RuntimeException("list work does not belong to this service");
        }
        SubServiceWork updated = subServiceWorkRepository.save(subServiceWork);

        return new SubServiceWorkResponse(
                updated.getId(),
                updated.getDescription()
        );
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

    // Delete Sub Service
    public void deleteSubService(Long serviceId, Long subServiceId){
        SubService subService = subServiceRepository.findById(subServiceId)
                .orElseThrow(() -> new RuntimeException("Sub Service not Found"));
        if (!subService.getService().getId().equals(serviceId)){
            throw new RuntimeException("Sub service nto belong to this services");
        }

        subServiceRepository.deleteById(subServiceId);
    }

    public void deleteSubServiceWork(Long subServiceId, Long subServiceWorkId){
        SubServiceWork subServiceWork = subServiceWorkRepository.findById(subServiceWorkId)
                .orElseThrow(() -> new RuntimeException("Sub Service not Found"));
        if (!subServiceWork.getSubService().getId().equals(subServiceId)){
            throw new RuntimeException("Sub service nto belong to this services");
        }

        subServiceRepository.deleteById(subServiceWorkId);
    }
    private ServiceResponse mapToResponse(ServiceEntity service) {
        return ServiceResponse.builder()
                .id(service.getId())
                .name(service.getName())
                .shortDesc(service.getShortDesc())
                .longDesc(service.getLongDesc())
                .imageUrl(service.getImageUrl())
                .features(service.getFeatures() != null ?
                        service.getFeatures().stream()
                                .map(f -> new ServiceFeatureResponse(f.getId(), f.getFeatureName(), f.getFeatureDesc()))
                                .toList()
                        : List.of())
                .subServices(service.getSubServices() != null ?
                        service.getSubServices().stream()
                                .map(s -> SubServiceResponse.builder()
                                        .id(s.getId())
                                        .name(s.getName())
                                        .description(s.getDescription())
                                        .works(s.getWorks() != null ?
                                                s.getWorks().stream()
                                                        .map(w -> new SubServiceWorkResponse(w.getId(), w.getDescription()))
                                                        .toList()
                                                : List.of())
                                        .build())
                                .toList()
                        : List.of())
                .build();
    }

}
