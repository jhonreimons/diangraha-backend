package com.diangraha_backend.diangraha_backend.service;

import com.diangraha_backend.diangraha_backend.dto.SubServiceWorkRequest;
import com.diangraha_backend.diangraha_backend.entity.SubService;
import com.diangraha_backend.diangraha_backend.entity.SubServiceWork;
import com.diangraha_backend.diangraha_backend.repository.SubServiceRepository;
import com.diangraha_backend.diangraha_backend.repository.SubServiceWorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubServiceWorkService {

    private final SubServiceWorkRepository subServiceWorkRepository;
    private final SubServiceRepository subServiceRepository;

    // Ambil semua work dari sub-service tertentu
    public List<SubServiceWork> getAll(Long subServiceId) {
        return subServiceWorkRepository.findBySubService_Id(subServiceId);
    }

    // Tambah work baru
    public SubServiceWork create(Long subServiceId, SubServiceWorkRequest request) {
        SubService subService = subServiceRepository.findById(subServiceId)
                .orElseThrow(() -> new RuntimeException("SubService not found"));

        SubServiceWork work = SubServiceWork.builder()
                .description(request.getDescription())
                .subService(subService)
                .build();

        return subServiceWorkRepository.save(work);
    }

    // Update work tertentu
    public SubServiceWork update(Long subServiceId, Long id, SubServiceWorkRequest request) {
        SubServiceWork existing = subServiceWorkRepository.findById(id)
                .filter(w -> w.getSubService().getId().equals(subServiceId))
                .orElseThrow(() -> new RuntimeException("Work not found"));

        existing.setDescription(request.getDescription());

        return subServiceWorkRepository.save(existing);
    }

    // Hapus work tertentu
    public void delete(Long subServiceId, Long id) {
        SubServiceWork existing = subServiceWorkRepository.findById(id)
                .filter(w -> w.getSubService().getId().equals(subServiceId))
                .orElseThrow(() -> new RuntimeException("Work not found"));

        subServiceWorkRepository.delete(existing);
    }
}
