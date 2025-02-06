package com.chucky.project.controller;

import com.chucky.project.dto.FiltreTransactionDTO;
import com.chucky.project.dto.TransactionCryptoDTO;
import com.chucky.project.dto.TransactionDTO;
import com.chucky.project.dto.TransactionFondDTO;
import com.chucky.project.dto.TransactionFondRequestDto;
import com.chucky.project.model.TransactionFond;
import com.chucky.project.service.StatutService;
import com.chucky.project.service.TransactionFondService;
import com.chucky.project.service.TransactionService;
import com.chucky.project.service.TypeTransactionService;

import jakarta.servlet.http.HttpSession;

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
    public List<TransactionFondDTO> findAll(HttpSession session) {
        Integer idU = (Integer) session.getAttribute("utilisateur_id"); 
        return transactionfondService.findAllByUserID(idU);
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
    public ResponseEntity<?> effectuerDepot(@RequestBody TransactionFondRequestDto dto, HttpSession session) {
        Integer idU = (Integer) session.getAttribute("utilisateur_id"); 
        try {
            TransactionFond transaction = transactionService.depot(
                idU,
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
    public ResponseEntity<?> effectuerRetrait(@RequestBody TransactionFondRequestDto dto,HttpSession session) {
        Integer idU = (Integer) session.getAttribute("utilisateur_id"); 
        try {
            TransactionFond transaction = transactionService.retrait(
                idU, 
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

    @GetMapping("/depot")
    public List<TransactionDTO> findDepotAttente(){
        return transactionfondService.findByStatutAttenteDepot();
    }


    
    @GetMapping("/retrait")
    public List<TransactionDTO> findRetraitAttente(){
        return transactionfondService.findByStatutAttenteRetrait();
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


     @PostMapping("/filtre")
    public List<TransactionFondDTO> filtreParDate(@RequestBody FiltreTransactionDTO filtreTransactionDTO ){
        return transactionfondService.findByFilters(filtreTransactionDTO.getDebut(), filtreTransactionDTO.getFin(),filtreTransactionDTO.getUtilisateurId());
    }


    @GetMapping("/filtre/{id}")
    public List<TransactionFondDTO> filtreParUtilisateur(@PathVariable Integer id){
        return transactionfondService.findByUtilisateur(id);
    }
}
