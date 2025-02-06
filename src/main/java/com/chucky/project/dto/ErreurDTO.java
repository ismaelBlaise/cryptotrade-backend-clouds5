package com.chucky.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ErreurDTO {
    @JsonProperty("error")
    private String error;
    @JsonProperty("message")
    private String message;
}
