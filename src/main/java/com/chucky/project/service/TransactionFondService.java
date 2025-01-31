package com.chucky.project.service;

import com.chucky.project.model.TransactionFond;
import com.chucky.project.repository.TransactionFondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TransactionFondService {

    @Autowired
    private TransactionFondRepository transactionfondRepository;

    public TransactionFond save(TransactionFond transactionfond) {
        return transactionfondRepository.save(transactionfond);
    }

    public List<TransactionFond> findAll() {
        return transactionfondRepository.findAll();
    }

    public TransactionFond findById(Integer id) {
        return transactionfondRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public TransactionFond update(Integer id, TransactionFond transactionfond) {
        TransactionFond existingTransactionFond = findById(id);
        transactionfond.setId(existingTransactionFond.getId());
        return transactionfondRepository.save(transactionfond);
    }

    public void delete(Integer id) {
        transactionfondRepository.deleteById(id);
    }
    
}
