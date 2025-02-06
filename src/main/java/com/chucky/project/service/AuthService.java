package com.chucky.project.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.chucky.project.dto.CodePinDTO;
import com.chucky.project.dto.EmailDTO;
import com.chucky.project.dto.ErreurDTO;
import com.chucky.project.dto.IdDTO;
import com.chucky.project.dto.InscriptionDTO;
import com.chucky.project.dto.ValidationDTO;
import com.chucky.project.dto.LoginDTO;
import com.chucky.project.dto.TokenDTO;
import com.chucky.project.dto.UtilisateurDTO;
import com.chucky.project.dto.UtilisateurEtatDTO;
import com.chucky.project.model.Role;
import com.chucky.project.model.Utilisateur;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class AuthService {
    
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private RoleService roleService;


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
        Role role=roleService.findRoleUtilisateur();
        u.setRoleId(role.getId());
        
        return u;
        // u.setPhoto(data.getPhoto());
    }


    public CodePinDTO connexionAPI(LoginDTO loginDTO) {
        Utilisateur utilisateur = connexion(loginDTO);
        // UtilisateurEtatDTO utilisateurEtatDTO = recupererUtilisateurAPI(loginDTO.getEmail());
        // if(utilisateurEtatDTO.getNbTentative() ==0){
        //     CodePinDTO codePinDTO=new CodePinDTO();
        //     codePinDTO.setCodepin(Integer.valueOf(-123));
        //     codePinDTO.setEmail(loginDTO.getEmail());

        //     return codePinDTO;
        // }
        if (utilisateur == null) {
            throw new RuntimeException("L'utilisateur n'existe pas.");
        }
    
        try {
                return webClient.post()
                    .uri("/utilisateurs/connexion")
                    .bodyValue(loginDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                        if (clientResponse.statusCode().value() == 404) {
                            return Mono.error(new RuntimeException("Trop de tentatives de connexion. Veuillez consulter votre boite email."));
                        }
                        return clientResponse.bodyToMono(ErreurDTO.class)
                                .map(body -> new RuntimeException(body.getMessage()));
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(ErreurDTO.class)
                            .map(body -> new RuntimeException("Erreur serveur : " + body.getMessage()))
                    )
                    .bodyToMono(CodePinDTO.class)
                    .block();
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
    }
    
    public TokenDTO validationPinAPI(CodePinDTO codePinDTO) {
        try {
            return webClient.post()
                .uri("/utilisateurs/valider-pin")
                .bodyValue(codePinDTO)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(ErreurDTO.class)
                        .map(body -> new RuntimeException(body.getMessage()))
                )
                .bodyToMono(TokenDTO.class)  
                .block();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    

    public ValidationDTO inscriptionAPI(InscriptionDTO inscriptionDTO) {
        try {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setEmail(inscriptionDTO.getEmail());
            loginDTO.setMdp(inscriptionDTO.getMdp());
            if (utilisateurService.check(loginDTO) == true) {
                throw new RuntimeException("Email déja utilisé.");
            }
           

            return webClient.post()
                .uri("/utilisateurs/inscrire")
                .bodyValue(inscriptionDTO)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(ErreurDTO.class)
                        .map(body -> new RuntimeException( body.getMessage()))
                )
                .bodyToMono(ValidationDTO.class)
                .block();
        } catch (RuntimeException e) {
            throw new RuntimeException("Inscription erreur : " + e.getMessage(), e);
        }  
    }

    public UtilisateurEtatDTO recupererUtilisateurAPI(String email) {
        try {
            EmailDTO emailDTO=new EmailDTO();
            emailDTO.setEmail(email);
            UtilisateurEtatDTO utilisateurEtatDTO = webClient.post()
                .uri("/utilisateurs/recuperer-compte")   
                .bodyValue(emailDTO)  
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(ErreurDTO.class)
                        .map(body -> {
                            return new RuntimeException("Erreur API : " + body.getMessage());
                        })
                )
                .bodyToMono(UtilisateurEtatDTO.class)  
                .block();  
    
            return utilisateurEtatDTO;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public UtilisateurDTO recupererUtilisateurAPIParId(Integer id) {
        try {
            Utilisateur utilisateur=utilisateurService.findById(id);
            IdDTO idDTO=new IdDTO();
            idDTO.setId(utilisateur.getFournisseurId());
            UtilisateurDTO utilisateurDTO = webClient.post()
                .uri("/utilisateurs/recuperer-utilisateur")   
                .bodyValue(idDTO)  
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(ErreurDTO.class)
                        .map(body -> {
                            return new RuntimeException("Erreur API : " + body.getMessage());
                        })
                )
                .bodyToMono(UtilisateurDTO.class)  
                .block();  
            utilisateurDTO.setPhoto(utilisateur.getPhoto());
            return utilisateurDTO;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public List<UtilisateurDTO> findAll(){
        List<Utilisateur> us=utilisateurService.findAll();
        List<UtilisateurDTO> utilisateurDTOs=new ArrayList<>();
        for (Utilisateur utilisateur : us) {
            UtilisateurDTO utilisateurDTO=recupererUtilisateurAPIParId(utilisateur.getId());
            utilisateurDTO.setIdU(utilisateur.getId());
            utilisateurDTOs.add(utilisateurDTO);
        }

        return utilisateurDTOs;
    } 

}
