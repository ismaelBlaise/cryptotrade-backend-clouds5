package com.chucky.project.controller;

import com.chucky.project.model.Portefeuille;
import com.chucky.project.model.Utilisateur;
import com.chucky.project.service.PortefeuilleService;
import com.chucky.project.service.UtilisateurService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/portefeuilles")
public class PortefeuilleController {

    @Autowired
    private PortefeuilleService portefeuilleService;

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Portefeuille portefeuille) throws Exception{
        try {
            
    
            portefeuilleService.save(portefeuille);

            return ResponseEntity.ok(portefeuille);
    
          
        } catch (Exception e) {
            
            return ResponseEntity.status(500).body(e.getMessage());  
        }
    }
    

    @GetMapping
    public BigDecimal findAll(HttpSession session) {
        Utilisateur utilisateur=utilisateurService.findById((Integer) session.getAttribute("utilisateur_id"));
        return utilisateur.getPortefeuille().getMontant();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Portefeuille> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(portefeuilleService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Portefeuille> update(@PathVariable Integer id, @RequestBody Portefeuille portefeuille) {
        return ResponseEntity.ok(portefeuilleService.update(id, portefeuille));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        portefeuilleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
