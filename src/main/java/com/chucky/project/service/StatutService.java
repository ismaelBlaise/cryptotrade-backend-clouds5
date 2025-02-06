package com.chucky.project.service;

import com.chucky.project.model.Statut;
import com.chucky.project.repository.StatutRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StatutService {

    @Autowired
    private StatutRepository statutRepository;

    public Statut save(Statut statut) {
        return statutRepository.save(statut);
    }

    public List<Statut> findAll() {
        return statutRepository.findAll();
    }

    public Statut findById(Integer id) {
        return statutRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public Statut update(Integer id, Statut statut) {
        Statut existingStatut = findById(id);
        statut.setId(existingStatut.getId());
        return statutRepository.save(statut);
    }

    public void delete(Integer id) {
        statutRepository.deleteById(id);
    }

    public Statut getStatutAttente() {
        return statutRepository.findByStatut("attente").orElseThrow(() -> new RuntimeException("statut introuvable"));
    }

    public Statut getStatutRefus() {
        return statutRepository.findByStatut("refuser").orElseThrow(() -> new RuntimeException("statut introuvable"));
    }

    public Statut getStatutValider() {
        return statutRepository.findByStatut("valider").orElseThrow(() -> new RuntimeException("statut introuvable"));
    }
    
}
