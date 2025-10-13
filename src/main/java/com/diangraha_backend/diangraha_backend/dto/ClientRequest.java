package com.diangraha_backend.diangraha_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

    private Long id;
    private String name;
    private String imageUrl;
}
