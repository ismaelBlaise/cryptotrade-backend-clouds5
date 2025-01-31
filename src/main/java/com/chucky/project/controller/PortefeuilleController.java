package com.chucky.project.controller;

import com.chucky.project.model.Portefeuille;
import com.chucky.project.service.PortefeuilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portefeuilles")
public class PortefeuilleController {

    @Autowired
    private PortefeuilleService portefeuilleService;

    @PostMapping
    public Portefeuille save(@RequestBody Portefeuille portefeuille) {
        return portefeuilleService.save(portefeuille);
    }

    @GetMapping
    public List<Portefeuille> findAll() {
        return portefeuilleService.findAll();
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
