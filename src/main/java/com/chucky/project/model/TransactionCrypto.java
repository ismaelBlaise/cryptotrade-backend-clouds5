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
@Table(name = "transaction_crypto")
public class TransactionCrypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "montant")
    private BigDecimal montant;

    @Column(name = "date_creation")
    private Timestamp dateCreation;

    @Column(name = "validation_token")
    private String validationToken;

    @ManyToOne
    @JoinColumn(name = "statut_id")
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "type_transaction_id")
    private TypeTransaction typeTransaction;

    @ManyToOne
    @JoinColumn(name = "portefeuille_crypto_id")
    private PortefeuilleCrypto portefeuilleCrypto;

    public TransactionCrypto() {}
    public TransactionCrypto(BigDecimal quantite, BigDecimal montant, Statut statut, TypeTransaction typeTransaction, PortefeuilleCrypto portefeuilleCrypto) {
        setQuantite(quantite);
        setDateCreation(Timestamp.valueOf(LocalDateTime.now()));
        setMontant(montant);
        setStatut(statut);
        setTypeTransaction(typeTransaction);
        setPortefeuilleCrypto(portefeuilleCrypto);
    }

}
