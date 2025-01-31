package com.chucky.project.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("unused")
@Data
@Entity
@Table(name = "historique_prix")
public class HistoriquePrix {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "prix")
    private BigDecimal prix;

    @Column(name = "date_enregistrement")
    private Timestamp dateEnregistrement;

    @Column(name = "cryptomonnaie_id")
    private Integer cryptomonnaieId;


}
