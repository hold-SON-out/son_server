package com.example.hackathon_summer.domain.dto.auth.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @NotNull
    private String id;

    @NotNull
    private String password;

}
