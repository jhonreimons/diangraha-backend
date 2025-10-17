package com.diangraha_backend.diangraha_backend.dto;

import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class SubServiceWorkResponse {
    private Long id;
    private String description;
}
