package com.chucky.project.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class TransactionDTO {
    private Integer id;
    private BigDecimal montant;
    private String email;
    private Timestamp dateCreation;
}
