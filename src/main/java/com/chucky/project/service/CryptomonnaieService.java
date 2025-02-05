package com.chucky.project.service;

import com.chucky.project.model.Cryptomonnaie;
import com.chucky.project.repository.CryptomonnaieRepository;
import com.chucky.project.repository.HistoriquePrixRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CryptomonnaieService {

    @Autowired
    private CryptomonnaieRepository cryptomonnaieRepository;
    
    @Autowired
    private HistoriquePrixRepository historiquePrixRepository; 

    public Cryptomonnaie save(Cryptomonnaie cryptomonnaie) {
        return cryptomonnaieRepository.save(cryptomonnaie);
    }

    public List<Cryptomonnaie> findAll() {
        List<Cryptomonnaie> cryptomonnaies=cryptomonnaieRepository.findAll();
        for (Cryptomonnaie cryptomonnaie : cryptomonnaies) {
            cryptomonnaie.setPrix(historiquePrixRepository.findLatestByCryptomonnaieId(cryptomonnaie.getId()).get().getPrix());
        }
        return cryptomonnaies;
    }

    public Cryptomonnaie findById(Integer id) {
        return cryptomonnaieRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public Cryptomonnaie update(Integer id, Cryptomonnaie cryptomonnaie) {
        Cryptomonnaie existingCryptomonnaie = findById(id);
        cryptomonnaie.setId(existingCryptomonnaie.getId());
        return cryptomonnaieRepository.save(cryptomonnaie);
    }

    public void delete(Integer id) {
        cryptomonnaieRepository.deleteById(id);
    }
    
}
