package com.chucky.project.repository;

import com.chucky.project.model.Statut;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutRepository extends JpaRepository<Statut,Integer> {

    Optional<Statut> findByStatut(String statut);

}
