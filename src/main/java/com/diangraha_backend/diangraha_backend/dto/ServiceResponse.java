package com.diangraha_backend.diangraha_backend.dto;

import com.diangraha_backend.diangraha_backend.entity.SubServiceWork;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ServiceResponse {
    private Long id;
    private String name;
    private String shortDesc;
    private String longDesc;
    private String imageUrl;
    private List<ServiceFeatureResponse> features;
    private List<SubServiceResponse> subService;
    private List<SubServiceWorkResponse> subServiceWorks;
}

