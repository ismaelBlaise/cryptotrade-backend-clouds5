package com.chucky.project.controller;

import com.chucky.project.model.TypeCommission;
import com.chucky.project.service.TypeCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/typecommissions")
public class TypeCommissionController {

    @Autowired
    private TypeCommissionService typecommissionService;

    @PostMapping
    public TypeCommission save(@RequestBody TypeCommission typecommission) {
        return typecommissionService.save(typecommission);
    }

    @GetMapping
    public List<TypeCommission> findAll() {
        return typecommissionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeCommission> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(typecommissionService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeCommission> update(@PathVariable Integer id, @RequestBody TypeCommission typecommission) {
        return ResponseEntity.ok(typecommissionService.update(id, typecommission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        typecommissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
