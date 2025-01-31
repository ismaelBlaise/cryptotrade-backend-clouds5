package com.chucky.project.model;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("unused")
@Data
@Entity
@Table(name = "cryptomonnaie")
public class Cryptomonnaie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "symbole")
    private String symbole;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

}
