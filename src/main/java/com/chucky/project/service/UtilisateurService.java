package com.chucky.project.service;

import com.chucky.project.dto.LoginDTO;
import com.chucky.project.model.Utilisateur;
import com.chucky.project.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur findById(Integer id) {
        return utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public Utilisateur update(Integer id, Utilisateur utilisateur) {
        Utilisateur existingUtilisateur = findById(id);
        utilisateur.setId(existingUtilisateur.getId());
        return utilisateurRepository.save(utilisateur);
    }

    public void delete(Integer id) {
        utilisateurRepository.deleteById(id);
    }

    public Utilisateur findByEmailAndMpd(LoginDTO data) {
        return utilisateurRepository.findByEmailAndMdp(data.getEmail(), data.getMdp()).orElseThrow(() -> new RuntimeException("erreur lors de l'authentification"));
    }

    public Utilisateur findByEmail(LoginDTO data) {
        return utilisateurRepository.findByEmail(data.getEmail()).orElseThrow(() -> new RuntimeException("erreur lors de l'authentification"));
    }
    
}
