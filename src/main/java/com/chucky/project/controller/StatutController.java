package com.chucky.project.controller;

import com.chucky.project.model.Statut;
import com.chucky.project.service.StatutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuts")
public class StatutController {

    @Autowired
    private StatutService statutService;

    @PostMapping
    public Statut save(@RequestBody Statut statut) {
        return statutService.save(statut);
    }

    @GetMapping
    public List<Statut> findAll() {
        return statutService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Statut> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(statutService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Statut> update(@PathVariable Integer id, @RequestBody Statut statut) {
        return ResponseEntity.ok(statutService.update(id, statut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        statutService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
