package com.diangraha_backend.diangraha_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ContactMessageResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String interestedIn;
    private String message;
    private Instant createdAt;
}