package com.chucky.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class IdDTO {
    @JsonProperty("id")
    Integer id;
}
