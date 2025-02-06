package com.chucky.project.controller;

import com.chucky.project.dto.FiltreTransactionDTO;
import com.chucky.project.dto.TransactionCryptoDTO;
import com.chucky.project.dto.TransactionCryptoRequestDto;
import com.chucky.project.model.TransactionCrypto;
import com.chucky.project.service.StatutService;
import com.chucky.project.service.TransactionCryptoService;
import com.chucky.project.service.TransactionService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
    public ResponseEntity<?> effectuerAchat(@RequestBody TransactionCryptoRequestDto dto, HttpSession session) {
        try {
            Integer idU=(Integer) session.getAttribute("utilisateur_id");
            @SuppressWarnings("unused")
            Integer achatId = transactionService.achat(idU, dto.getIdCrypto(), dto.getQuantite());
            return ResponseEntity.ok("Achat effectuer veuilliez consulter votre email pour valider");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/vente")
    public ResponseEntity<?> effectuerVente(@RequestBody TransactionCryptoRequestDto dto,HttpSession session) {
        try {
            Integer idU=(Integer) session.getAttribute("utilisateur_id");
            TransactionCrypto transaction = transactionService.vente(
                idU,
                dto.getIdCrypto(), 
                dto.getQuantite()
            );
            return ResponseEntity.ok("Vente de "+transaction.getQuantite()+" "+transaction.getPortefeuilleCrypto().getCryptomonnaie().getNom()+" effectuée !");
        } catch (RuntimeException e) {
            e.printStackTrace();
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
            
            TransactionCrypto transactionCrypto = transactionService.validaterAchat(token);
            
             
            String cryptoName = transactionCrypto.getPortefeuilleCrypto().getCryptomonnaie().getNom();
            String cryptoSymbol = transactionCrypto.getPortefeuilleCrypto().getCryptomonnaie().getSymbole();
            String creationDate = transactionCrypto.getDateCreation().toString();
            
            
            String message = "<b>Votre transaction de " + cryptoName + " (" + cryptoSymbol + ")</b><br>"
                        + "Date de création: " + creationDate + "<br>"
                        + "a été validée avec succès.";
            
            
            String htmlResponse = "<html>"
                                + "<head>"
                                + "<style>"
                                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }"
                                + "h1 { text-align: center; color: #4CAF50; margin-top: 50px; font-size: 24px; }"
                                + "div.container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center; }"
                                + "p { font-size: 18px; color: #333; text-align: center; margin-top: 20px; }"
                                + "b { color: #4CAF50; }"
                                + "</style>"
                                + "</head>"
                                + "<body>"
                                + "<div class='container'>"
                                + "<h1>" + message + "</h1>"
                                + "</div>"
                                + "</body>"
                                + "</html>";
            
            return ResponseEntity.ok().body(htmlResponse);
        } catch (RuntimeException e) {
            
            String errorHtml = "<html>"
                            + "<head>"
                            + "<style>"
                            + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }"
                            + "h1 { text-align: center; color: #FF6347; margin-top: 50px; font-size: 24px; }"
                            + "div.container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center; }"
                            + "p { font-size: 18px; color: #333; text-align: center; margin-top: 20px; }"
                            + "b { color: #FF6347; }"
                            + "</style>"
                            + "</head>"
                            + "<body>"
                            + "<div class='container'>"
                            + "<h1>Erreur: " + e.getMessage() + "</h1>"
                            + "</div>"
                            + "</body>"
                            + "</html>";
                            
            return ResponseEntity.badRequest().body(errorHtml);
        }
    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
    }


    @PostMapping("/filtre")
    public List<TransactionCryptoDTO> filtreParDate(@RequestBody FiltreTransactionDTO filtreTransactionDTO ){
        return transactioncryptoService.findByFilters(filtreTransactionDTO.getDebut(), filtreTransactionDTO.getFin(),filtreTransactionDTO.getUtilisateurId(),filtreTransactionDTO.getCryptoId());
    }


    @GetMapping("/filtre/{id}")
    public List<TransactionCryptoDTO> filtreParUtilisateur(@PathVariable Integer id){
        return transactioncryptoService.findByUtilisateur(id);
    }

}
