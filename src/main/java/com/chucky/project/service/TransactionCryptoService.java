package com.chucky.project.service;

import com.chucky.project.model.TransactionCrypto;
import com.chucky.project.repository.TransactionCryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionCryptoService {

    @Autowired
    private TransactionCryptoRepository transactioncryptoRepository;

    @Autowired
    private StatutService statutService;
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
        if(!optTransaction.isPresent()){
            throw new RuntimeException("Transaction non trouver");

        }
        if(optTransaction.get().getStatut().equals(statutService.getStatutValider())){
            throw new RuntimeException("Transaction deja valider");
        }

        if(optTransaction.get().getStatut().equals(statutService.getStatutRefus())){
            throw new RuntimeException("Transaction refuser");
        }
        return optTransaction.get();
    }
    
}
