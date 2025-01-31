package com.chucky.project.controller;

import com.chucky.project.model.Cryptomonnaie;
import com.chucky.project.service.CryptomonnaieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cryptomonnaies")
public class CryptomonnaieController {

    @Autowired
    private CryptomonnaieService cryptomonnaieService;

    @PostMapping
    public Cryptomonnaie save(@RequestBody Cryptomonnaie cryptomonnaie) {
        return cryptomonnaieService.save(cryptomonnaie);
    }

    @GetMapping
    public List<Cryptomonnaie> findAll() {
        return cryptomonnaieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cryptomonnaie> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(cryptomonnaieService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cryptomonnaie> update(@PathVariable Integer id, @RequestBody Cryptomonnaie cryptomonnaie) {
        return ResponseEntity.ok(cryptomonnaieService.update(id, cryptomonnaie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cryptomonnaieService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
