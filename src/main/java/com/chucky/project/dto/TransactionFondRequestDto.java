package com.chucky.project.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransactionFondRequestDto {
    @JsonProperty("montant")
    private BigDecimal montant;
}
