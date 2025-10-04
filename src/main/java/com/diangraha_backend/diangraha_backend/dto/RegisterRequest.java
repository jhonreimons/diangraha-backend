package com.diangraha_backend.diangraha_backend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String Username;

    @NotBlank
    private String password;
}
