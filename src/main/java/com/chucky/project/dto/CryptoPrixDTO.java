package com.chucky.project.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.chucky.project.model.Cryptomonnaie;

import lombok.Data;

@Data
public class CryptoPrixDTO {
    
    private Cryptomonnaie cryptomonnaie;
    private BigDecimal prix;
    private Timestamp dateChangement;
    public CryptoPrixDTO(Cryptomonnaie cryptomonnaie, BigDecimal prix, Timestamp dateChangement) {
        this.cryptomonnaie = cryptomonnaie;
        this.prix = prix;
        this.dateChangement = dateChangement;
    }
}
