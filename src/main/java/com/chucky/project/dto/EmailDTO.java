package com.chucky.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmailDTO {
    @JsonProperty("email")
    String email;
}
