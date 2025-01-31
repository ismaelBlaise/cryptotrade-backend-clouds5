package com.chucky.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CodePinDTO {

    @JsonProperty("email")
    private String email;
    @JsonProperty("codepin")
    private Integer codepin;
}
