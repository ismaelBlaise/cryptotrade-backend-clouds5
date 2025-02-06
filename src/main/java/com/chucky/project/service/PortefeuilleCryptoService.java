package com.chucky.project.service;

import com.chucky.project.model.Cryptomonnaie;
import com.chucky.project.model.HistoriquePrix;
import com.chucky.project.model.PortefeuilleCrypto;
import com.chucky.project.model.Utilisateur;
import com.chucky.project.repository.HistoriquePrixRepository;
import com.chucky.project.repository.PortefeuilleCryptoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PortefeuilleCryptoService {

    @Autowired
    private PortefeuilleCryptoRepository portefeuillecryptoRepository;

    @Autowired
    private HistoriquePrixRepository historiquePrixRepository;

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
        if(portefeuillecryptoRepository.findByCryptomonnaieAndUtilisateur(cryptomonnaie, utilisateur).isPresent()){
            return portefeuillecryptoRepository.findByCryptomonnaieAndUtilisateur(cryptomonnaie, utilisateur).get();
        } else {
            return null;
        }
    }


    public List<Cryptomonnaie> findCryptoByUtilisateur(Utilisateur utilisateur) {
        List<Cryptomonnaie> cryptomonnaies = new ArrayList<>();
        List<PortefeuilleCrypto> portefeuilleCryptos=portefeuillecryptoRepository.findByUtilisateur(utilisateur);
        for (PortefeuilleCrypto portefeuilleCrypto : portefeuilleCryptos) {
            HistoriquePrix historiquePrix=historiquePrixRepository.findLatestByCryptomonnaieId(portefeuilleCrypto.getCryptomonnaie().getId()).get();
            portefeuilleCrypto.getCryptomonnaie().setPrix(historiquePrix.getPrix());
            portefeuilleCrypto.getCryptomonnaie().setQuantite(portefeuilleCrypto.getQuantite());
            cryptomonnaies.add(portefeuilleCrypto.getCryptomonnaie());
            
        }
        return cryptomonnaies;


    }
    
}
