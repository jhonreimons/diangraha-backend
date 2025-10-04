package com.diangraha_backend.diangraha_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;

    // Constructor yang benar harus sama dengan nama class
    public LoginResponse(String token) {
        this.token = token;
    }
}
