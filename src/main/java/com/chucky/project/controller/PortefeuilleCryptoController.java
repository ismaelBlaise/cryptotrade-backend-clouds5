package com.chucky.project.controller;

import com.chucky.project.model.PortefeuilleCrypto;
import com.chucky.project.service.PortefeuilleCryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portefeuillecryptos")
public class PortefeuilleCryptoController {

    @Autowired
    private PortefeuilleCryptoService portefeuillecryptoService;

    @PostMapping
    public PortefeuilleCrypto save(@RequestBody PortefeuilleCrypto portefeuillecrypto) {
        return portefeuillecryptoService.save(portefeuillecrypto);
    }

    @GetMapping
    public List<PortefeuilleCrypto> findAll() {
        return portefeuillecryptoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortefeuilleCrypto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(portefeuillecryptoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortefeuilleCrypto> update(@PathVariable Integer id, @RequestBody PortefeuilleCrypto portefeuillecrypto) {
        return ResponseEntity.ok(portefeuillecryptoService.update(id, portefeuillecrypto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        portefeuillecryptoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
