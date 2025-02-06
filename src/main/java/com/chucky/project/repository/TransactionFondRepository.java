package com.chucky.project.repository;

import com.chucky.project.model.Portefeuille;
import com.chucky.project.model.Statut;
import com.chucky.project.model.TransactionFond;
import com.chucky.project.model.TypeTransaction;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionFondRepository extends JpaRepository<TransactionFond,Integer> {
    List<TransactionFond>findAllByPortefeuille(Portefeuille portefeuille);
    List<TransactionFond>findByStatutAndTypeTransaction(Statut statut,TypeTransaction typeTransaction);


    @Query("SELECT t FROM TransactionFond t " +
    "WHERE t.dateCreation >= :dateDebut AND t.dateCreation <= :dateFin")
    List<TransactionFond> findByFiltreDate(@Param("dateDebut") Timestamp dateDebut, @Param("dateFin") Timestamp dateFin);

    @Query("SELECT t FROM TransactionFond t " +
       "WHERE (:utilisateurId IS NULL OR t.portefeuille.utilisateur.id = :utilisateurId)")
    List<TransactionFond> findByUtilisateur(@Param("utilisateurId") Integer utilisateurId);
}
