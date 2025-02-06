package com.chucky.project.service;

import com.chucky.project.model.Cryptomonnaie;
import com.chucky.project.model.HistoriquePrix;
import com.chucky.project.repository.CryptomonnaieRepository;
import com.chucky.project.repository.HistoriquePrixRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PrixGenerationService {

    @Autowired
    private CryptomonnaieRepository cryptomonnaieRepository;

    @Autowired
    private HistoriquePrixRepository historiquePrixRepository;

    private final Random random = new Random();

    private static final BigDecimal PRIX_MINIMAL = BigDecimal.valueOf(0.01);

    @Scheduled(fixedRate = 10000)
    public void genererPrixAleatoire() {
        List<Cryptomonnaie> cryptomonnaies = cryptomonnaieRepository.findAll();

        for (Cryptomonnaie crypto : cryptomonnaies) {
            Optional<HistoriquePrix> dernierPrixOpt = historiquePrixRepository
                .findLatestByCryptomonnaieId(crypto.getId());
            if(!dernierPrixOpt.isPresent()){
                HistoriquePrix historiquePrix = new HistoriquePrix();
                historiquePrix.setCryptomonnaieId(crypto.getId());
                historiquePrix.setPrix(BigDecimal.valueOf(random.nextDouble(1000, 10000)));
                historiquePrix.setDateEnregistrement(Timestamp.valueOf(LocalDateTime.now()));
            }
            BigDecimal dernierPrix = dernierPrixOpt
                .map(HistoriquePrix::getPrix)
                .orElse(BigDecimal.valueOf(500)); 

            double variation = (random.nextDouble() * 1.6) - 0.8;
            BigDecimal facteurVariation = BigDecimal.valueOf(1 + variation);
            BigDecimal prixActualise = dernierPrix.multiply(facteurVariation).setScale(2, RoundingMode.HALF_UP);

            if (prixActualise.compareTo(PRIX_MINIMAL) < 0) {
                prixActualise = PRIX_MINIMAL.multiply(BigDecimal.valueOf(10000)); // Augmentation de 200%
            }

            HistoriquePrix historiquePrix = new HistoriquePrix();
            historiquePrix.setCryptomonnaieId(crypto.getId());
            historiquePrix.setPrix(prixActualise);
            historiquePrix.setDateEnregistrement(Timestamp.valueOf(LocalDateTime.now()));

            historiquePrixRepository.save(historiquePrix);
        }
    }
}