package com.chucky.project.service;

import com.chucky.project.model.Cryptomonnaie;
import com.chucky.project.model.PortefeuilleCrypto;
import com.chucky.project.model.Utilisateur;
import com.chucky.project.repository.PortefeuilleCryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PortefeuilleCryptoService {

    @Autowired
    private PortefeuilleCryptoRepository portefeuillecryptoRepository;

    public PortefeuilleCrypto save(PortefeuilleCrypto portefeuillecrypto) {
        return portefeuillecryptoRepository.save(portefeuillecrypto);
    }

    public List<PortefeuilleCrypto> findAll() {
        return portefeuillecryptoRepository.findAll();
    }

    public PortefeuilleCrypto findById(Integer id) {
        return portefeuillecryptoRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public PortefeuilleCrypto update(Integer id, PortefeuilleCrypto portefeuillecrypto) {
        PortefeuilleCrypto existingPortefeuilleCrypto = findById(id);
        portefeuillecrypto.setId(existingPortefeuilleCrypto.getId());
        return portefeuillecryptoRepository.save(portefeuillecrypto);
    }

    public void delete(Integer id) {
        portefeuillecryptoRepository.deleteById(id);
    }

    public PortefeuilleCrypto findByCryptomonnaieAndUtilisateur(Cryptomonnaie cryptomonnaie, Utilisateur utilisateur) {
        return portefeuillecryptoRepository.findByCryptomonnaieAndUtilisateur(cryptomonnaie, utilisateur).orElseThrow(() -> new RuntimeException("portefeuille crypto introuvable"));
    }
    
}
