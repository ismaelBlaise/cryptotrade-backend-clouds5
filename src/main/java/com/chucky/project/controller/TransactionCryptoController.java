package com.chucky.project.controller;

import com.chucky.project.dto.TransactionCryptoRequestDto;
import com.chucky.project.model.TransactionCrypto;
import com.chucky.project.service.StatutService;
import com.chucky.project.service.TransactionCryptoService;
import com.chucky.project.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transactioncryptos")
public class TransactionCryptoController {

    @Autowired
    private TransactionCryptoService transactioncryptoService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StatutService statutService;

    @PostMapping
    public TransactionCrypto save(@RequestBody TransactionCrypto transactioncrypto) {
        return transactioncryptoService.save(transactioncrypto);
    }

    @GetMapping
    public List<TransactionCrypto> findAll() {
        return transactioncryptoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionCrypto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(transactioncryptoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionCrypto> update(@PathVariable Integer id, @RequestBody TransactionCrypto transactioncrypto) {
        return ResponseEntity.ok(transactioncryptoService.update(id, transactioncrypto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        transactioncryptoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/achat")
    public ResponseEntity<?> effectuerAchat(@RequestBody TransactionCryptoRequestDto dto) {
        try {
            Integer achatId = transactionService.achat(dto.getIdUtilisateur(), dto.getIdCrypto(), dto.getQuantite());
            return ResponseEntity.ok(achatId);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/vente")
    public ResponseEntity<?> effectuerVente(@RequestBody TransactionCryptoRequestDto dto) {
        try {
            TransactionCrypto transaction = transactionService.vente(
                dto.getIdUtilisateur(), 
                dto.getIdCrypto(), 
                dto.getQuantite()
            );
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verifier-achat")
    public ResponseEntity<?> verifierAchat(@RequestParam Integer idTransation) {
        try {
            TransactionCrypto transaction=transactioncryptoService.findById(idTransation);
            if(statutService.getStatutAttente().equals(transaction.getStatut())){
                return ResponseEntity.ok(false);
            }
            if(statutService.getStatutRefus().equals(transaction.getStatut())){
                throw new RuntimeException("Achat refusée");
            }
            return ResponseEntity.ok(true);
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/valider-achat")
    public ResponseEntity<String> validerAchat(@RequestParam String token) {
        try {
            transactionService.validaterAchat(token);
            return ResponseEntity.ok("Achat validé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
    }

}
