package com.diangraha_backend.diangraha_backend.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubServiceResponse {
    private Long id;
    private String name;
    private String description;
    private List<SubServiceWorkResponse> works;
}
