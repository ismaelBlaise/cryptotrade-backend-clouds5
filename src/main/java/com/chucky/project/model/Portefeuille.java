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
@Table(name = "portefeuille")
public class Portefeuille {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "montant")
    private BigDecimal montant;

    @Column(name = "date_enregistrement")
    private Timestamp dateEnregistrement;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    public void montantPlus(BigDecimal montant) {
        setMontant(BigDecimal.valueOf(getMontant().doubleValue() + montant.doubleValue()));
    }

    public void montantMoins(BigDecimal montant) {
        if (getMontant().doubleValue() < montant.doubleValue()) throw new RuntimeException("Solde insuffisante");
        setMontant(BigDecimal.valueOf(getMontant().doubleValue() - montant.doubleValue()));
    }

}
