package com.chucky.project.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InscriptionDTO {
    @JsonProperty("email")
    private String email;
    @JsonProperty("nom")
    private String nom;
    @JsonProperty("prenom")
    private String prenom;
    @JsonProperty("date_naissance")
    private LocalDate dateNaissance;  
    @JsonProperty("mot_de_passe")
    private String mdp;
    @JsonIgnore
    private String photo;
    @JsonProperty("sexe")
    private Integer idSexe;
}
