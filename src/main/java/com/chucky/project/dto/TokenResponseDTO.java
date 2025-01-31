package com.chucky.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TokenResponseDTO {
    @JsonProperty("token")
    private String token;
}
