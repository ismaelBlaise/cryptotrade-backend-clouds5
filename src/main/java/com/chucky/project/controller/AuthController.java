package com.chucky.project.controller;

import com.chucky.project.dto.CodePinDTO;
import com.chucky.project.dto.InscriptionDTO;
import com.chucky.project.dto.LoginDTO;
import com.chucky.project.dto.TokenResponseDTO;
import com.chucky.project.dto.UtilisateurEtatDTO;
import com.chucky.project.model.Utilisateur;
import com.chucky.project.service.AuthService;
import com.chucky.project.service.UtilisateurService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService; 

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private HttpSession session;

    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody LoginDTO loginDTO) {
        try {
            CodePinDTO codePinDTO = authService.connexionAPI(loginDTO);
            loginDTO.setMdp(null);
            session.setAttribute("logindto", loginDTO);
            return ResponseEntity.ok(codePinDTO); 
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }


    @PostMapping("/valider-pin")
    public ResponseEntity<?> validationPin(@RequestBody CodePinDTO codePinDTO) {
        try {
            TokenResponseDTO tokenResponse = authService.validationPinAPI(codePinDTO);
            LoginDTO loginDTO=(LoginDTO) session.getAttribute("logindto");
            Utilisateur utilisateur=utilisateurService.findByEmail(loginDTO);
            
            session.setAttribute("utilisateur_id",utilisateur.getId());
            session.setAttribute("fournisseur_id",utilisateur.getFournisseurId());
            session.setAttribute("token", tokenResponse.getToken());
            session.removeAttribute("logindto");
            return ResponseEntity.ok(tokenResponse);  
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }


    @PostMapping("/inscrire")
    public ResponseEntity<?> inscription(@RequestBody InscriptionDTO inscriptionDTO) {
        try {
            String response = authService.inscriptionAPI(inscriptionDTO);
            session.setAttribute("email",inscriptionDTO.getEmail());
            inscriptionDTO.setMdp(null);
            session.setAttribute("inscriptiondto", inscriptionDTO);
            return ResponseEntity.ok(response);   
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }


    @PostMapping("/recuperer")
    public ResponseEntity<?> recupererUtilisateur() {
        try {
            String email=(String) session.getAttribute("email");
            UtilisateurEtatDTO utilisateurEtat = authService.recupererUtilisateurAPI(email);
            
            if(utilisateurEtat.isEtat()){
                InscriptionDTO inscriptionDTO=(InscriptionDTO) session.getAttribute("inscriptiondto");
                authService.inscription(inscriptionDTO);
                session.removeAttribute("inscriptiondto");
            }
            return ResponseEntity.ok(utilisateurEtat.isEtat()); 
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }


    
}
