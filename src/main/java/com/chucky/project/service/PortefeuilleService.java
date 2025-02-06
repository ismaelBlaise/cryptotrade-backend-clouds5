package com.chucky.project.service;

import com.chucky.project.model.Portefeuille;
import com.chucky.project.repository.PortefeuilleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PortefeuilleService {

    @Autowired
    private PortefeuilleRepository portefeuilleRepository;

    public Portefeuille save(Portefeuille portefeuille) {
        return portefeuilleRepository.save(portefeuille);
    }

    public List<Portefeuille> findAll() {
        return portefeuilleRepository.findAll();
    }

    public Portefeuille findById(Integer id) {
        return portefeuilleRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public Portefeuille update(Integer id, Portefeuille portefeuille) {
        Portefeuille existingPortefeuille = findById(id);
        portefeuille.setId(existingPortefeuille.getId());
        return portefeuilleRepository.save(portefeuille);
    }

    public void delete(Integer id) {
        portefeuilleRepository.deleteById(id);
    }
    
}
