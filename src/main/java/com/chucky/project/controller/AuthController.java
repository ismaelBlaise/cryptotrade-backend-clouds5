package com.chucky.project.controller;

import com.chucky.project.dto.AuthDTO;
import com.chucky.project.dto.CodePinDTO;
import com.chucky.project.dto.InscriptionDTO;
import com.chucky.project.dto.ValidationDTO;
import com.chucky.project.dto.LoginDTO;
import com.chucky.project.dto.TokenDTO;
import com.chucky.project.dto.UtilisateurDTO;
import com.chucky.project.dto.UtilisateurEtatDTO;
import com.chucky.project.model.Utilisateur;
import com.chucky.project.service.AuthService;
import com.chucky.project.service.RoleService;
import com.chucky.project.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private RoleService roleService;

    

    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody LoginDTO loginDTO, HttpSession session) {
        try {
            CodePinDTO codePinDTO = authService.connexionAPI(loginDTO);
            // if (codePinDTO.getCodepin() == -123) {
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Trop de tentatives de connexion. Veuillez consulter votre boite email.");
            // }
    
            loginDTO.setMdp(null);
            session.setAttribute("logindto", loginDTO);
            return ResponseEntity.ok(codePinDTO); 
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }
    
    @PostMapping("/valider-pin")
    public ResponseEntity<?> validationPin(@RequestBody CodePinDTO codePinDTO, HttpSession session) {
        try {
            LoginDTO loginDTO = (LoginDTO) session.getAttribute("logindto");
            
            TokenDTO tokenResponse = authService.validationPinAPI(codePinDTO);
            Utilisateur utilisateur = utilisateurService.findByEmail(loginDTO);

            session.setAttribute("utilisateur_id", utilisateur.getId());
            session.setAttribute("fournisseur_id", utilisateur.getFournisseurId());
            session.setAttribute("token", tokenResponse.getToken());
            session.removeAttribute("logindto");
            AuthDTO authDTO=new AuthDTO();
            authDTO.setRole(roleService.findById(utilisateur.getRoleId()).getNom());
            authDTO.setToken(tokenResponse.getToken());
            return ResponseEntity.ok(authDTO);  
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }

    @PostMapping("/inscrire")
    public ResponseEntity<?> inscription(@RequestBody InscriptionDTO inscriptionDTO,HttpSession session) {
        try {
            ValidationDTO response = authService.inscriptionAPI(inscriptionDTO);
            session.setAttribute("email",inscriptionDTO.getEmail());
            inscriptionDTO.setMdp(null);
            session.setAttribute("inscriptiondto", inscriptionDTO);
            return ResponseEntity.ok(response);   
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }


    @PostMapping("/recuperer")
    public ResponseEntity<?> recupererUtilisateur(HttpSession session) {
        try {
            String email=(String) session.getAttribute("email");
            UtilisateurEtatDTO utilisateurEtat = authService.recupererUtilisateurAPI(email);
            
            

            if(utilisateurEtat.isEtat()){
                InscriptionDTO inscriptionDTO=(InscriptionDTO) session.getAttribute("inscriptiondto");
                Utilisateur u=authService.inscription(inscriptionDTO);
                u.setFournisseurId(utilisateurEtat.getId());
                u.setMdp(utilisateurEtat.getMdp());
                utilisateurService.save(u);
                session.removeAttribute("inscriptiondto");
            }
            return ResponseEntity.ok(utilisateurEtat.isEtat()); 
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }

    @PostMapping("/recuperer-utilisateur")
    public ResponseEntity<?> recupererUtilisateurParId(@RequestParam Integer id) {
        try {
            
            UtilisateurDTO utilisateur= authService.recupererUtilisateurAPIParId(id);
            
            return ResponseEntity.ok(utilisateur); 
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());  
        }
    }



    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
         
        session.invalidate();
        
         
        return ResponseEntity.ok("Déconnexion réussie !");
    }


    
}
