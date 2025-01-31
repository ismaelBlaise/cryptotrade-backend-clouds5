package com.chucky.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionCryptoRequestDto {
    private Integer idUtilisateur;
    private Integer idCrypto;
    private BigDecimal quantite;
}

