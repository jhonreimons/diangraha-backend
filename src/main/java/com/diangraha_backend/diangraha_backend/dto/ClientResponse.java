package com.diangraha_backend.diangraha_backend.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponse {
    private Long id;
    private  String name;
    private  String imageUrl;
}
