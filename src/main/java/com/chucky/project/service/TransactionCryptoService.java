package com.chucky.project.service;

import com.chucky.project.dto.TransactionCryptoDTO;
import com.chucky.project.dto.UtilisateurDTO;
import com.chucky.project.model.TransactionCrypto;
import com.chucky.project.model.Utilisateur;
import com.chucky.project.repository.TransactionCryptoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionCryptoService {

    @Autowired
    private TransactionCryptoRepository transactioncryptoRepository;

    @Autowired 
    private AuthService authService;

    public TransactionCrypto save(TransactionCrypto transactioncrypto) {
        return transactioncryptoRepository.save(transactioncrypto);
    }

    public List<TransactionCrypto> findAll() {
        return transactioncryptoRepository.findAll();
    }

    public TransactionCrypto findById(Integer id) {
        return transactioncryptoRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public TransactionCrypto update(Integer id, TransactionCrypto transactioncrypto) {
        TransactionCrypto existingTransactionCrypto = findById(id);
        transactioncrypto.setId(existingTransactionCrypto.getId());
        return transactioncryptoRepository.save(transactioncrypto);
    }

    public void delete(Integer id) {
        transactioncryptoRepository.deleteById(id);
    }

    public TransactionCrypto findByValidationToken(String token){
        Optional<TransactionCrypto> optTransaction=transactioncryptoRepository.findByValidationToken(token);
        return optTransaction.get();
    }


    public List<TransactionCryptoDTO> findByFilters(Timestamp dateDebut, Timestamp dateFin, Integer utilisateurId, Integer cryptoId) {
        List<TransactionCrypto> transactionCryptos;

         
        if (dateDebut != null && dateFin != null) {
            transactionCryptos = transactioncryptoRepository.findByFiltreDate(dateDebut, dateFin);
        } else {
            transactionCryptos = transactioncryptoRepository.findAll();  
        }

         
        if (utilisateurId != null) {
            List<TransactionCrypto> transactionsParUtilisateur = transactioncryptoRepository.findByUtilisateur(utilisateurId);
            transactionCryptos.retainAll(transactionsParUtilisateur); 
        }

        if (cryptoId != null) {
            List<TransactionCrypto> transactionsParCrypto = transactioncryptoRepository.findByCryptoId(cryptoId);
            transactionCryptos.retainAll(transactionsParCrypto);  
        }

         
        return transactionCryptos.stream().map(transactionCrypto -> {
            TransactionCryptoDTO dto = new TransactionCryptoDTO();
            UtilisateurDTO utilisateurDTO = authService.recupererUtilisateurAPIParId(transactionCrypto.getPortefeuilleCrypto().getUtilisateur().getId());
            dto.setNom(utilisateurDTO.getNom());
            dto.setPrenom(utilisateurDTO.getPrenom());
            dto.setDateTransaction(transactionCrypto.getDateCreation());
            dto.setMontant(transactionCrypto.getMontant());
            dto.setNomCrypto(transactionCrypto.getPortefeuilleCrypto().getCryptomonnaie().getNom());
            dto.setSymbole(transactionCrypto.getPortefeuilleCrypto().getCryptomonnaie().getSymbole());
            dto.setQuantite(transactionCrypto.getQuantite());
            dto.setType(transactionCrypto.getTypeTransaction().getTypeTransaction());
            return dto;
        }).collect(Collectors.toList());
    }

    

    public List<TransactionCryptoDTO> findByUtilisateur(Integer utilisateurId){
        List<TransactionCrypto> transactionCryptos=transactioncryptoRepository.findByUtilisateur(utilisateurId);
        List<TransactionCryptoDTO> transactionCryptoDTOs=new ArrayList<>();
        for (TransactionCrypto transactionCrypto : transactionCryptos) {
            TransactionCryptoDTO transactionCryptoDTO=new TransactionCryptoDTO();
            UtilisateurDTO utilisateurDTO=authService.recupererUtilisateurAPIParId(transactionCrypto.getPortefeuilleCrypto().getUtilisateur().getId());
            transactionCryptoDTO.setPhoto(transactionCrypto.getPortefeuilleCrypto().getUtilisateur().getPhoto());
            transactionCryptoDTO.setNom(utilisateurDTO.getNom());
            transactionCryptoDTO.setPrenom(utilisateurDTO.getPrenom());
            transactionCryptoDTO.setDateTransaction(transactionCrypto.getDateCreation());
            transactionCryptoDTO.setMontant(transactionCrypto.getMontant());
            transactionCryptoDTO.setNomCrypto(transactionCrypto.getPortefeuilleCrypto().getCryptomonnaie().getNom());
            transactionCryptoDTO.setSymbole(transactionCrypto.getPortefeuilleCrypto().getCryptomonnaie().getSymbole());
            transactionCryptoDTO.setQuantite(transactionCrypto.getQuantite());
            transactionCryptoDTO.setType(transactionCrypto.getTypeTransaction().getTypeTransaction());
            transactionCryptoDTOs.add(transactionCryptoDTO);
        }
        return transactionCryptoDTOs;
    }
    
}
