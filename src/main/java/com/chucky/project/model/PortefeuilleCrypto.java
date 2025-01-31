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
@Table(name = "portefeuille_crypto")
public class PortefeuilleCrypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "date_creation")
    private Timestamp dateCreation;

    @ManyToOne
    @JoinColumn(name = "cryptomonnaie_id")
    private Cryptomonnaie cryptomonnaie;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    public void quantitePlus(BigDecimal quantite) {
        setQuantite(BigDecimal.valueOf(getQuantite().doubleValue() + quantite.doubleValue()));
    }

    public void quantiteMoins(BigDecimal quantite) {
        if (getQuantite().doubleValue() < quantite.doubleValue()) throw new RuntimeException("quantite insuffisante");
        setQuantite(BigDecimal.valueOf(getQuantite().doubleValue() - quantite.doubleValue()));
    }

}
