package com.diangraha_backend.diangraha_backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubServiceResponse {
    private Long id;
    private String name;
    private String description;
    private List<SubServiceWorkResponse> works;
}
