package com.chucky.project.service;

import com.chucky.project.model.HistoriquePrix;
import com.chucky.project.repository.HistoriquePrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class HistoriquePrixService {

    @Autowired
    private HistoriquePrixRepository historiqueprixRepository;

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
    
}
