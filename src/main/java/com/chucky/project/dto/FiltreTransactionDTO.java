package com.chucky.project.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class FiltreTransactionDTO {
    private Timestamp debut;
    private Timestamp fin;
    private Integer utilisateurId;
    private Integer cryptoId;
}
