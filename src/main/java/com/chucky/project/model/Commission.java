package com.chucky.project.model;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("unused")
@Data
@Entity
@Table(name = "commission")
public class Commission {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "pourcentage")
    private BigDecimal pourcentage;

    @Column(name = "type_commission_id")
    private Integer typeCommissionId;


}
