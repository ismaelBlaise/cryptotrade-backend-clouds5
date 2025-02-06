package com.chucky.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UtilisateurEtatDTO {
    @JsonProperty("id_utilisateurs")
    private Integer id;
    @JsonProperty("mot_de_passe")
    private String mdp;
    @JsonProperty("nb_tentative")
    private Integer nbTentative;
    @JsonProperty("etat")
    private boolean etat;
}
