package com.chucky.project.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class TransactionFondDTO {
    BigDecimal montant;
    Timestamp dateCreation;
    String typeTransaction;
    String statut;
    String nom;
    String prenom;
    String photo;
    
}
