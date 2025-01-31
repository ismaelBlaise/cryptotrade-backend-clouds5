package com.chucky.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UtilisateurEtatDTO {
    @JsonProperty("id_utilisateurs")
    private Integer id;
    @JsonProperty("etat")
    private boolean etat;
}
