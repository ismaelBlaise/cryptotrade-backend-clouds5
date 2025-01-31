package com.chucky.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginDTO {
    @JsonProperty("email")
    private String email;
    @JsonProperty("mot_de_passe")
    private String mdp;
}
