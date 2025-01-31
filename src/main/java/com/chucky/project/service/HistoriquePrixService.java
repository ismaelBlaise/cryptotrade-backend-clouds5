package com.chucky.project.service;

import com.chucky.project.dto.CryptoPrixDTO;
import com.chucky.project.model.Cryptomonnaie;
import com.chucky.project.model.HistoriquePrix;
import com.chucky.project.repository.HistoriquePrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HistoriquePrixService {

    @Autowired
    private HistoriquePrixRepository historiqueprixRepository;

    @Autowired
    private CryptomonnaieService cryptomonnaieService;

    public HistoriquePrix save(HistoriquePrix historiqueprix) {
        return historiqueprixRepository.save(historiqueprix);
    }

    public List<HistoriquePrix> findAll() {
        return historiqueprixRepository.findAll();
    }

    public HistoriquePrix findById(Integer id) {
        return historiqueprixRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public HistoriquePrix update(Integer id, HistoriquePrix historiqueprix) {
        HistoriquePrix existingHistoriquePrix = findById(id);
        historiqueprix.setId(existingHistoriquePrix.getId());
        return historiqueprixRepository.save(historiqueprix);
    }

    public void delete(Integer id) {
        historiqueprixRepository.deleteById(id);
    }

    public HistoriquePrix findLatestByCryptomonnaieId(Integer cryptoId) {
        return historiqueprixRepository.findLatestByCryptomonnaieId(cryptoId).orElseThrow(() -> new RuntimeException("Historique prix introuvable"));
    }

    public List<CryptoPrixDTO> findLatestPricesForAllCryptomonnaies() {
        List<CryptoPrixDTO> cryptoPrixDTOs = new ArrayList<>();
        List<HistoriquePrix> historiquePrixes = historiqueprixRepository.findLatestPricesForAllCryptomonnaies();
        for (HistoriquePrix historiquePrix : historiquePrixes) {
            Cryptomonnaie cryptomonnaie=cryptomonnaieService.findById(historiquePrix.getCryptomonnaieId());
            BigDecimal prix = historiquePrix.getPrix();
            Timestamp timestamp = historiquePrix.getDateEnregistrement();
            cryptoPrixDTOs.add(new CryptoPrixDTO(cryptomonnaie, prix,timestamp));
        }
        return cryptoPrixDTOs;
    }

    public List<CryptoPrixDTO> findCoursCrypto(Integer cryptoId){
        List<CryptoPrixDTO> cryptoPrixDTOs = new ArrayList<>();
        List<HistoriquePrix> historiquePrixes = historiqueprixRepository.findAllByCryptomonnaieId(cryptoId);
        for (HistoriquePrix historiquePrix : historiquePrixes) {
            Cryptomonnaie cryptomonnaie=cryptomonnaieService.findById(historiquePrix.getCryptomonnaieId());
            BigDecimal prix = historiquePrix.getPrix();
            Timestamp timestamp = historiquePrix.getDateEnregistrement();
            cryptoPrixDTOs.add(new CryptoPrixDTO(cryptomonnaie, prix,timestamp));
        }
        return cryptoPrixDTOs;
    }
}

