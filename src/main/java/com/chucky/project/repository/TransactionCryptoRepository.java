package com.chucky.project.repository;

import com.chucky.project.model.TransactionCrypto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCryptoRepository extends JpaRepository<TransactionCrypto,Integer> {
    Optional<TransactionCrypto> findByValidationToken(String token);

    @Query("SELECT t FROM TransactionCrypto t " +
       "WHERE  t.dateCreation >= :dateDebut AND  t.dateCreation <= :dateFin")
    List<TransactionCrypto> findByFiltreDate(@Param("dateDebut") Timestamp dateDebut,@Param("dateFin") Timestamp dateFin);

    
    @Query("SELECT t FROM TransactionCrypto t " +
       "WHERE (:cryptoId IS NULL OR t.portefeuilleCrypto.cryptomonnaie.id = :cryptoId)")
    List<TransactionCrypto> findByCryptoId(@Param("cryptoId") Integer cryptoId);


    @Query("SELECT t FROM TransactionCrypto t " +
    "WHERE (:utilisateurId IS NULL OR t.portefeuilleCrypto.utilisateur.id = :utilisateurId)")
    List<TransactionCrypto> findByUtilisateur(@Param("utilisateurId") Integer utilisateurId);

}
