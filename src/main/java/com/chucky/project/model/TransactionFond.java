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
@Table(name = "transaction_fond")
public class TransactionFond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "montant")
    private BigDecimal montant;

    @Column(name = "date_creation")
    private Timestamp dateCreation;

    @ManyToOne
    @JoinColumn(name = "statut_id")
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "type_transaction_id")
    private TypeTransaction typeTransaction;

    @ManyToOne
    @JoinColumn(name = "portefeuille_id")
    private Portefeuille portefeuille;

    @Column(name = "transaction_crypto_id")
    private Integer transactionCrypto;

    public TransactionFond() {}
    public TransactionFond(BigDecimal montant, Statut statut, Portefeuille portefeuille, TypeTransaction typeTransaction) {
        setMontant(montant);
        setDateCreation(Timestamp.valueOf(LocalDateTime.now()));
        setStatut(statut);
        setPortefeuille(portefeuille);
        setTypeTransaction(typeTransaction);
    }

}
