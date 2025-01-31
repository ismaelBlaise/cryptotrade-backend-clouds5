package com.chucky.project.controller;

import com.chucky.project.model.HistoriquePrix;
import com.chucky.project.service.HistoriquePrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historiqueprixs")
public class HistoriquePrixController {

    @Autowired
    private HistoriquePrixService historiqueprixService;

    @PostMapping
    public HistoriquePrix save(@RequestBody HistoriquePrix historiqueprix) {
        return historiqueprixService.save(historiqueprix);
    }

    @GetMapping
    public List<HistoriquePrix> findAll() {
        return historiqueprixService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriquePrix> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(historiqueprixService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoriquePrix> update(@PathVariable Integer id, @RequestBody HistoriquePrix historiqueprix) {
        return ResponseEntity.ok(historiqueprixService.update(id, historiqueprix));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        historiqueprixService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cours")
    public ResponseEntity<?>  coursEnTempsReel() {
        try {
            return ResponseEntity.ok(historiqueprixService.findLatestPricesForAllCryptomonnaies());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cours/{id}")
    public ResponseEntity<?>  coursCrypto(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(historiqueprixService.findCoursCrypto(id));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
