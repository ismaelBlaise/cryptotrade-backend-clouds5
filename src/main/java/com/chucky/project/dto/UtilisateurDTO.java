package com.chucky.project.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UtilisateurDTO {
    @JsonIgnore
    private Integer idU;
    @JsonProperty("id_utilisateurs")
    private Integer id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("nom")
    private String nom;
    @JsonProperty("prenom")
    private String prenom;
    @JsonProperty("date_naissance")
    private Date dateNaissance;
    @JsonProperty("mot_de_passe")
    private String mdp;
    @JsonProperty("etat")
    private boolean etat;
    @JsonProperty("nb_tentative")
    private Integer nbTentative;
    @JsonProperty("id_sexe")
    private Integer idSexe;
    @JsonIgnore
    private String photo;
}
