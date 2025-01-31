package com.chucky.project.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chucky.project.model.Cryptomonnaie;
import com.chucky.project.model.HistoriquePrix;
import com.chucky.project.model.Portefeuille;
import com.chucky.project.mail.*;
import com.chucky.project.model.PortefeuilleCrypto;
import com.chucky.project.model.Statut;
import com.chucky.project.model.TransactionCrypto;
import com.chucky.project.model.TransactionFond;
import com.chucky.project.model.Utilisateur;

import jakarta.mail.MessagingException;

@Service
public class TransactionService {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PortefeuilleService portefeuilleService;

    @Autowired
    private PortefeuilleCryptoService portefeuilleCryptoService;

    @Autowired
    private TransactionFondService transactionFondService;

    @Autowired
    private StatutService statutService;

    @Autowired
    private TypeTransactionService typeTransactionService;

    @Autowired
    private CryptomonnaieService cryptomonnaieService;

    @Autowired
    private HistoriquePrixService historiquePrixService;

    @Autowired
    private TransactionCryptoService transactionCryptoService;
    
    public TransactionFond depot(Integer idUtilisateur, BigDecimal montant,Statut statut,Integer transactionCrypto) {
        Utilisateur u = utilisateurService.findById(idUtilisateur);
        Portefeuille portefeuille = u.getPortefeuille();
        if(portefeuille==null){
            portefeuille=new Portefeuille();
            portefeuille.setMontant(BigDecimal.valueOf(0));
            portefeuille.setUtilisateur(u);
            // portefeuille.setDateEnregistrement(Timestamp.valueOf(LocalDateTime.now()));
            portefeuilleService.save(portefeuille);
        }
        TransactionFond transaction = new TransactionFond(montant,statut, portefeuille, typeTransactionService.getTypeDepot());
        transaction.setTransactionCrypto(transactionCrypto);
        return transactionFondService.save(transaction);
    }

    public TransactionFond retrait(Integer idUtilisateur, BigDecimal montant,Statut statut,Integer transactionCrypto) {
        Utilisateur u = utilisateurService.findById(idUtilisateur);
        Portefeuille portefeuille = u.getPortefeuille();
        TransactionFond transaction = new TransactionFond(montant, statut, portefeuille, typeTransactionService.getTypeRetrait());
        transaction.setTransactionCrypto(transactionCrypto);
        return transactionFondService.save(transaction);
    }



    public void validerDepot(TransactionFond depot){
        depot.setStatut(statutService.getStatutValider());
        Portefeuille portefeuille=depot.getPortefeuille();
        portefeuille.montantPlus(depot.getMontant());
        portefeuilleService.update(portefeuille.getId(), portefeuille);
        transactionFondService.update(depot.getId(), depot);

    }


    public void validerRetrait(TransactionFond retrait){
        retrait.setStatut(statutService.getStatutValider());
        Portefeuille portefeuille=retrait.getPortefeuille();
        portefeuille.montantMoins(retrait.getMontant());
        portefeuilleService.update(portefeuille.getId(), portefeuille);
        transactionFondService.update(retrait.getId(), retrait);
        
    }



    public void refuserDepot(TransactionFond depot){
        if(depot.getStatut().equals(statutService.getStatutAttente())){
            depot.setStatut(statutService.getStatutRefus());
            Portefeuille portefeuille=depot.getPortefeuille();
            portefeuille.montantMoins(depot.getMontant());
            transactionFondService.update(depot.getId(), depot);
        }
        else if(depot.getStatut().equals(statutService.getStatutValider())){
            throw new RuntimeException("Le depot est déja valider");
        }

        else if(depot.getStatut().equals(statutService.getStatutRefus())){
            throw new RuntimeException("Le depot est déja refuser");
        }
        

    }


    public void refuserRetrait(TransactionFond retrait){
        if(retrait.getStatut().equals(statutService.getStatutAttente())){
            retrait.setStatut(statutService.getStatutRefus());
            Portefeuille portefeuille=retrait.getPortefeuille();
            portefeuille.montantPlus(retrait.getMontant());
            transactionFondService.update(retrait.getId(), retrait);
        }
        else if(retrait.getStatut().equals(statutService.getStatutValider())){
            throw new RuntimeException("Le retrait est déja valider");
        }

        else if(retrait.getStatut().equals(statutService.getStatutRefus())){
            throw new RuntimeException("Le retrait est déja refuser");
        }
    }

    public void validaterAchat(String token){
        
        TransactionCrypto transactionCrypto=transactionCryptoService.findByValidationToken(token);
        if(transactionCrypto.getStatut().equals(statutService.getStatutValider())){
            throw new RuntimeException("Achat deja valider");
        }
        if(transactionCrypto.getStatut().equals(statutService.getStatutRefus())){
            throw new RuntimeException("Achat refuser");
        }
        retrait(transactionCrypto.getPortefeuilleCrypto().getUtilisateur().getId(),transactionCrypto.getMontant(), statutService.getStatutValider(),transactionCrypto.getId());
        PortefeuilleCrypto portefeuilleCrypto=transactionCrypto.getPortefeuilleCrypto();
        portefeuilleCrypto.quantitePlus(transactionCrypto.getQuantite());
        portefeuilleCryptoService.update(portefeuilleCrypto.getId(), portefeuilleCrypto);
        transactionCrypto.setStatut(statutService.getStatutValider());

    }

    @SuppressWarnings("unused")
    public Integer achat(Integer idUtilisateur, Integer idCrypto, BigDecimal quantite) {
        Utilisateur u = utilisateurService.findById(idUtilisateur);
        Cryptomonnaie crypto = cryptomonnaieService.findById(idCrypto);
        PortefeuilleCrypto portefeuille = portefeuilleCryptoService.findByCryptomonnaieAndUtilisateur(crypto, u);
        if(portefeuille==null){
            portefeuille=new PortefeuilleCrypto();
            portefeuille.setCryptomonnaie(crypto);
            portefeuille.setQuantite(BigDecimal.valueOf(0));
            portefeuille.setUtilisateur(u);
            portefeuilleCryptoService.save(portefeuille);
        }
        HistoriquePrix historiquePrix = historiquePrixService.findLatestByCryptomonnaieId(idCrypto);
        BigDecimal montant = BigDecimal.valueOf(quantite.doubleValue() * historiquePrix.getPrix().doubleValue());
        TransactionCrypto transaction = new TransactionCrypto(quantite, montant, statutService.getStatutAttente(), typeTransactionService.getTypeAchat(), portefeuille);
        String token =UUID.randomUUID().toString();
        transaction.setValidationToken(token);
        transactionCryptoService.save(transaction);
        String urlValidation=emailValidationAchat(token, u.getEmail());

        return transaction.getId();
    }

    public Statut statutAchat(Integer transactionId){
        TransactionCrypto transactionCrypto=transactionCryptoService.findById(transactionId);
        return transactionCrypto.getStatut();
    }

    public String emailValidationAchat(String token, String destinataire) {
        EmailConfig config = new EmailConfig("smtp.gmail.com", 587, "rarianamiadana@gmail.com", "rvprckgprzaukjkk");
        EmailService emailService = new EmailService(config);
    
         
        String urlValidation = "http://localhost:8080/transactioncryptos/valider-achat?token=" + token;
    
         
        String contenuHTML = String.format(
            "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "    <meta charset='UTF-8'>" +
            "    <title>Validation de votre achat</title>" +
            "    <style>" +
            "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; text-align: center; }" +
            "        .container { background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); width: 80%%; max-width: 500px; margin: auto; }" +
            "        .button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #ffffff; background-color: #28a745; text-decoration: none; border-radius: 5px; margin-top: 20px; }" +
            "        .button:hover { background-color: #218838; }" +
            "        .footer { margin-top: 20px; font-size: 12px; color: #666; }" +
            "    </style>" +
            "</head>" +
            "<body>" +
            "    <div class='container'>" +
            "        <h2>Confirmation de votre achat</h2>" +
            "        <p>Merci pour votre achat ! Pour finaliser votre commande, veuillez cliquer sur le bouton ci-dessous :</p>" +
            "        <a href='%s' class='button'>Valider mon achat</a>" +
            "        <p class='footer'>Si vous n'êtes pas à l'origine de cette demande, ignorez simplement cet e-mail.</p>" +
            "    </div>" +
            "</body>" +
            "</html>",
            urlValidation
        );
    
        try {
            emailService.sendEmail(destinataire, "Confirmation d'achat", contenuHTML);
            return urlValidation;
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email : " + e.getMessage(), e);
        }
    }
    


    

    public TransactionCrypto vente(Integer idUtilisateur, Integer idCrypto, BigDecimal quantite) {
        Utilisateur u = utilisateurService.findById(idUtilisateur);
        Cryptomonnaie crypto = cryptomonnaieService.findById(idCrypto);
        PortefeuilleCrypto portefeuille = portefeuilleCryptoService.findByCryptomonnaieAndUtilisateur(crypto, u);
        if(portefeuille==null){
            throw new RuntimeException("Vous ne possédez pas ce cryptomonnaie");
        }
        portefeuille.quantiteMoins(quantite);
        portefeuilleCryptoService.update(portefeuille.getId(), portefeuille);
        HistoriquePrix historiquePrix = historiquePrixService.findLatestByCryptomonnaieId(idCrypto);
        BigDecimal montant = BigDecimal.valueOf(quantite.doubleValue() * historiquePrix.getPrix().doubleValue());
        TransactionCrypto transaction = new TransactionCrypto(quantite, montant, statutService.getStatutValider(), typeTransactionService.getTypeVente(), portefeuille);
        depot(idUtilisateur, montant,statutService.getStatutValider(),transaction.getId());
        return transactionCryptoService.save(transaction);
    }

}
