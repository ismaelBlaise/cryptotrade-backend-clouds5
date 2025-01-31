package com.chucky.project.model;

import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("unused")
@Data
@Entity
@Table(name = "type_transaction")
public class TypeTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type_transaction")
    private String typeTransaction;


}
