package com.chucky.project.service;

import com.chucky.project.model.TypeTransaction;
import com.chucky.project.repository.TypeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TypeTransactionService {

    @Autowired
    private TypeTransactionRepository typetransactionRepository;

    public TypeTransaction save(TypeTransaction typetransaction) {
        return typetransactionRepository.save(typetransaction);
    }

    public List<TypeTransaction> findAll() {
        return typetransactionRepository.findAll();
    }

    public TypeTransaction findById(Integer id) {
        return typetransactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public TypeTransaction update(Integer id, TypeTransaction typetransaction) {
        TypeTransaction existingTypeTransaction = findById(id);
        typetransaction.setId(existingTypeTransaction.getId());
        return typetransactionRepository.save(typetransaction);
    }

    public void delete(Integer id) {
        typetransactionRepository.deleteById(id);
    }

    public TypeTransaction getTypeVente() {
        return typetransactionRepository.findByTypeTransaction("vente").orElseThrow(() -> new RuntimeException("type introuvable"));
    }

    public TypeTransaction getTypeAchat() {
        return typetransactionRepository.findByTypeTransaction("achat").orElseThrow(() -> new RuntimeException("type introuvable"));
    }

    public TypeTransaction getTypeDepot() {
        return typetransactionRepository.findByTypeTransaction("depot").orElseThrow(() -> new RuntimeException("type introuvable"));
    }

    public TypeTransaction getTypeRetrait() {
        return typetransactionRepository.findByTypeTransaction("retrait").orElseThrow(() -> new RuntimeException("type introuvable"));
    }
    
}
