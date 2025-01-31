package com.chucky.project.dto;

import java.math.BigDecimal;

import com.chucky.project.model.Cryptomonnaie;

import lombok.Data;

@Data
public class CryptoPrixDTO {
    
    private Cryptomonnaie cryptomonnaie;
    private BigDecimal prix;
    public CryptoPrixDTO(Cryptomonnaie cryptomonnaie, BigDecimal prix) {
        this.cryptomonnaie = cryptomonnaie;
        this.prix = prix;
    }
}
