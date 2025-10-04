package com.diangraha_backend.diangraha_backend.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest {
    private String name;
    private String shortDesc;
    private String longDesc;
    private MultipartFile imageFile;
}