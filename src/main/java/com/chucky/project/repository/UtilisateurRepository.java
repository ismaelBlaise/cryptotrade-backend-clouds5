package com.chucky.project.repository;

import com.chucky.project.model.Utilisateur;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {

    public Optional<Utilisateur> findByEmailAndMdp(String email,String mdp);

    public Optional<Utilisateur> findByEmail(String email);

    public Optional<Utilisateur> findByFournisseurId(Integer fournisseurId);

}
