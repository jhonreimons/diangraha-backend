package com.diangraha_backend.diangraha_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceFeatureResponse {
    private Long id;
    private String featureName;
    private String featureDesc;
}