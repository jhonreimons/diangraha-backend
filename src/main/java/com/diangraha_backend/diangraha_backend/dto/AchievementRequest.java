package com.diangraha_backend.diangraha_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementRequest {
    private String title;
    private String imageUrl;
}
