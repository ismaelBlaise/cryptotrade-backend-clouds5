package com.chucky.project.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransactionCryptoDTO {
    
    String nom;
    String prenom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    Timestamp dateTransaction;
    BigDecimal quantite;
    String photo;
    String nomCrypto;
    String symbole;
    BigDecimal montant;
    String type;

}
