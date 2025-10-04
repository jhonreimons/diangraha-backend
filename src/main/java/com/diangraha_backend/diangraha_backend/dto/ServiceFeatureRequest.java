package com.diangraha_backend.diangraha_backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ServiceFeatureRequest {
    private String featureName;
    private String featureDesc;
}
