package com.diangraha_backend.diangraha_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AchievementResponse {
    private Long id;
    private String title;
    private  String imageUrl;
}
