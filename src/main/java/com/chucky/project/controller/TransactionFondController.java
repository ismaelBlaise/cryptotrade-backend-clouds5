package com.chucky.project.controller;

import com.chucky.project.dto.TransactionFondRequestDto;
import com.chucky.project.model.TransactionFond;
import com.chucky.project.service.StatutService;
import com.chucky.project.service.TransactionFondService;
import com.chucky.project.service.TransactionService;
import com.chucky.project.service.TypeTransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/transactionfonds")
public class TransactionFondController {

    @Autowired
    private TransactionFondService transactionfondService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TypeTransactionService typeTransactionService;

    @Autowired
    private StatutService statutService;

    @PostMapping
    public TransactionFond save(@RequestBody TransactionFond transactionfond) {
        return transactionfondService.save(transactionfond);
    }

    @GetMapping
    public List<TransactionFond> findAll() {
        return transactionfondService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionFond> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(transactionfondService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionFond> update(@PathVariable Integer id, @RequestBody TransactionFond transactionfond) {
        return ResponseEntity.ok(transactionfondService.update(id, transactionfond));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        transactionfondService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/depot")
    public ResponseEntity<?> effectuerDepot(@RequestBody TransactionFondRequestDto dto) {
        try {
            TransactionFond transaction = transactionService.depot(
                dto.getIdUtilisateur(), 
                dto.getMontant(), 
                statutService.getStatutAttente(), 
                0
            );
            return ResponseEntity.ok("Dépôt ajouter en fil d'attente !");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/retrait")
    public ResponseEntity<?> effectuerRetrait(@RequestBody TransactionFondRequestDto dto) {
        try {
            
            TransactionFond transaction = transactionService.retrait(
                dto.getIdUtilisateur(), 
                dto.getMontant(), 
                statutService.getStatutAttente(), 
                0
            );
            return ResponseEntity.ok("Retrait ajouter en fil d'attente !");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/valider/{id}")
    public ResponseEntity<?> validerTransaction(@PathVariable Integer id) {
        try {

            TransactionFond transaction = transactionfondService.findById(id);
            if(transaction.getTypeTransaction().equals(typeTransactionService.getTypeDepot())){
                transactionService.validerDepot(transaction);
                return ResponseEntity.ok("Dépôt valider avec succès !");
            }
            else{
                transactionService.validerRetrait(transaction);
                return ResponseEntity.ok("Retrait valider avec succès !");
            }
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/refuser/{id}")
    public ResponseEntity<?> refuserTransaction(@PathVariable Integer id) {
        try {
            
            TransactionFond transaction = transactionfondService.findById(id);
            if(transaction.getTypeTransaction().equals(typeTransactionService.getTypeDepot())){
                transactionService.refuserDepot(transaction);
                return ResponseEntity.ok("Dépot refuser avec succès !");
            }
            else{
                transactionService.refuserRetrait(transaction);
                return ResponseEntity.ok("Retrait refuser avec succès !");
            }
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
