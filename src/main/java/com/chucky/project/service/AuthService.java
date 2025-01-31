package com.chucky.project.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.chucky.project.dto.CodePinDTO;
import com.chucky.project.dto.InscriptionDTO;
import com.chucky.project.dto.LoginDTO;
import com.chucky.project.dto.TokenResponseDTO;
import com.chucky.project.dto.UtilisateurEtatDTO;
import com.chucky.project.model.Utilisateur;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {
    
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private WebClient webClient;

    public Utilisateur connexion(LoginDTO data) {
        return utilisateurService.findByEmail(data);
    }

    public Utilisateur inscription(InscriptionDTO data) {
        Utilisateur u = new Utilisateur();
        u.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        u.setEmail(data.getEmail());
        u.setMdp("");
        u.setPhoto(data.getPhoto());
        return utilisateurService.save(u);
    }


    public CodePinDTO connexionAPI(LoginDTO loginDTO) {
        Utilisateur utilisateur = connexion(loginDTO);
    
        if (utilisateur == null) {
            throw new RuntimeException("L'utilisateur n'existe pas.");
        }
    
        try {
            return webClient.post()
                .uri("/utilisateurs/connexion")
                .bodyValue(loginDTO)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(body -> new RuntimeException(body))
                )
                .bodyToMono(CodePinDTO.class)
                .block(); 
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public TokenResponseDTO validationPinAPI(CodePinDTO codePinDTO) {
        try {
            return webClient.post()
                .uri("/utilisateurs/valider-pin")
                .bodyValue(codePinDTO)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(body -> new RuntimeException( body))
                )
                .bodyToMono(TokenResponseDTO.class)
                .block();
        } catch (RuntimeException e) {
            throw new RuntimeException( e.getMessage(), e);
        }
    }
    

    public String inscriptionAPI(InscriptionDTO inscriptionDTO) {
        try {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setEmail(inscriptionDTO.getEmail());
            loginDTO.setMdp(inscriptionDTO.getMdp());
            if (utilisateurService.findByEmail(loginDTO) != null) {
                throw new RuntimeException("Email déja utilisé.");
            }
            return webClient.post()
                .uri("/utilisateurs/inscrire")
                .bodyValue(inscriptionDTO)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(body -> new RuntimeException( body))
                )
                .bodyToMono(String.class)
                .block();
        } catch (RuntimeException e) {
            throw new RuntimeException( e.getMessage(), e);
        }  
    }

    public UtilisateurEtatDTO recupererUtilisateurAPI(String email) {
        try {
            
            UtilisateurEtatDTO utilisateurEtatDTO = webClient.post()
                .uri("/utilisateurs/recuperer-compte")   
                .bodyValue(Collections.singletonMap("email", email))  
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(String.class)
                        .map(body -> {
                            return new RuntimeException("Erreur API : " + body);
                        })
                )
                .bodyToMono(UtilisateurEtatDTO.class)  
                .block();  
    
            return utilisateurEtatDTO;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    


}
