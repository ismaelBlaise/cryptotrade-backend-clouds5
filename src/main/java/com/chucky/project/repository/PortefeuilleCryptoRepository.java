package com.chucky.project.repository;

import com.chucky.project.model.Cryptomonnaie;
import com.chucky.project.model.PortefeuilleCrypto;
import com.chucky.project.model.Utilisateur;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortefeuilleCryptoRepository extends JpaRepository<PortefeuilleCrypto,Integer> {

    Optional<PortefeuilleCrypto> findByCryptomonnaieAndUtilisateur(Cryptomonnaie cryptomonnaie, Utilisateur utilisateur);
    
    List<PortefeuilleCrypto> findByUtilisateur(Utilisateur utilisateur);
}
