package com.chucky.project.service;

import com.chucky.project.dto.TransactionDTO;
import com.chucky.project.dto.TransactionFondDTO;
import com.chucky.project.dto.UtilisateurDTO;
import com.chucky.project.model.Portefeuille;
import com.chucky.project.model.TransactionFond;
import com.chucky.project.model.Utilisateur;
import com.chucky.project.repository.TransactionFondRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionFondService {

    @Autowired
    private TransactionFondRepository transactionfondRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired 
    private StatutService statutService;
    
    @Autowired
    private AuthService authService;

    @Autowired 
    private TypeTransactionService typeTransactionService;

    public TransactionFond save(TransactionFond transactionfond) {
        return transactionfondRepository.save(transactionfond);
    }

    public List<TransactionFond> findAll() {
        return transactionfondRepository.findAll();
    }

    public List<TransactionFondDTO> findAllByUserID(Integer id) {
        Utilisateur utilisateur=utilisateurService.findById(id);
        Portefeuille portefeuille=utilisateur.getPortefeuille();
        List<TransactionFond> transactionFonds=transactionfondRepository.findAllByPortefeuille(portefeuille);
        List<TransactionFondDTO> transactionFondDTOs=new ArrayList<>();
        for (TransactionFond transactionFond : transactionFonds) {
            TransactionFondDTO transactionFondDTO=new TransactionFondDTO();
            transactionFondDTO.setMontant(transactionFond.getMontant());
            transactionFondDTO.setDateCreation(transactionFond.getDateCreation());
            transactionFondDTO.setTypeTransaction(transactionFond.getTypeTransaction().getTypeTransaction());
            transactionFondDTO.setStatut(transactionFond.getStatut().getStatut());    
            transactionFondDTOs.add(transactionFondDTO);
        }
        return transactionFondDTOs;
    }

    public List<TransactionDTO> findByStatutAttenteDepot() {
        List<TransactionFond> transactionFonds = transactionfondRepository.findByStatutAndTypeTransaction(statutService.getStatutAttente(),typeTransactionService.getTypeDepot());
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        for (TransactionFond transactionFond : transactionFonds) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setId(transactionFond.getId());
            transactionDTO.setDateCreation(transactionFond.getDateCreation());
            transactionDTO.setEmail(transactionFond.getPortefeuille().getUtilisateur().getEmail());
            transactionDTO.setMontant(transactionFond.getMontant());
            transactionDTOs.add(transactionDTO);
        }
        return transactionDTOs;
    }


    public List<TransactionDTO> findByStatutAttenteRetrait() {
        List<TransactionFond> transactionFonds = transactionfondRepository.findByStatutAndTypeTransaction(statutService.getStatutAttente(),typeTransactionService.getTypeRetrait());
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        for (TransactionFond transactionFond : transactionFonds) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setId(transactionFond.getId());
            transactionDTO.setDateCreation(transactionFond.getDateCreation());
            transactionDTO.setEmail(transactionFond.getPortefeuille().getUtilisateur().getEmail());
            transactionDTO.setMontant(transactionFond.getMontant());
            transactionDTOs.add(transactionDTO);
        }
        return transactionDTOs;
    }
    

    public TransactionFond findById(Integer id) {
        return transactionfondRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public TransactionFond update(Integer id, TransactionFond transactionfond) {
        TransactionFond existingTransactionFond = findById(id);
        transactionfond.setId(existingTransactionFond.getId());
        return transactionfondRepository.save(transactionfond);
    }

    public void delete(Integer id) {
        transactionfondRepository.deleteById(id);
    }


    public List<TransactionFondDTO> findByFilters(Timestamp dateDebut, Timestamp dateFin,Integer utilisateurId) {
        List<TransactionFond> transactionsFonds;

         
        if (dateDebut != null && dateFin != null) {
            transactionsFonds = transactionfondRepository.findByFiltreDate(dateDebut, dateFin);
        } else {
            transactionsFonds = transactionfondRepository.findAll();  
        }


        if (utilisateurId != null) {
            List<TransactionFond> transactionsParPortefeuille = transactionfondRepository.findByUtilisateur(utilisateurId);
            transactionsFonds.retainAll(transactionsParPortefeuille);  
        }

        return transactionsFonds.stream().map(transactionFond -> {
            TransactionFondDTO dto = new TransactionFondDTO();
            UtilisateurDTO u=authService.recupererUtilisateurAPIParId(transactionFond.getPortefeuille().getUtilisateur().getId());            dto.setMontant(transactionFond.getMontant());
            dto.setNom(u.getNom());
            dto.setPrenom(u.getPrenom());
            dto.setPhoto(u.getPhoto());
            dto.setMontant(transactionFond.getMontant());
            dto.setDateCreation(transactionFond.getDateCreation());
            dto.setTypeTransaction(transactionFond.getTypeTransaction().getTypeTransaction());
            dto.setStatut(transactionFond.getStatut().getStatut());
            return dto;
        }).collect(Collectors.toList());
    }


    public List<TransactionFondDTO> findByUtilisateur(Integer utilisateurId) {
        List<TransactionFond> transactionsFonds = transactionfondRepository.findByUtilisateur(utilisateurId);;


        return transactionsFonds.stream().map(transactionFond -> {
            TransactionFondDTO dto = new TransactionFondDTO();
            UtilisateurDTO u=authService.recupererUtilisateurAPIParId(transactionFond.getPortefeuille().getUtilisateur().getId());            dto.setMontant(transactionFond.getMontant());
            dto.setNom(u.getNom());
            dto.setPrenom(u.getPrenom());
            dto.setPhoto(u.getPhoto());
            dto.setMontant(transactionFond.getMontant());
            dto.setDateCreation(transactionFond.getDateCreation());
            dto.setTypeTransaction(transactionFond.getTypeTransaction().getTypeTransaction());
            dto.setStatut(transactionFond.getStatut().getStatut());
            return dto;
        }).collect(Collectors.toList());
    }




}   
