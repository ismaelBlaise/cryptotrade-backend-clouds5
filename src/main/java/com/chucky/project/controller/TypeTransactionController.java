package com.chucky.project.controller;

import com.chucky.project.model.TypeTransaction;
import com.chucky.project.service.TypeTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/typetransactions")
public class TypeTransactionController {

    @Autowired
    private TypeTransactionService typetransactionService;

    @PostMapping
    public TypeTransaction save(@RequestBody TypeTransaction typetransaction) {
        return typetransactionService.save(typetransaction);
    }

    @GetMapping
    public List<TypeTransaction> findAll() {
        return typetransactionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeTransaction> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(typetransactionService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeTransaction> update(@PathVariable Integer id, @RequestBody TypeTransaction typetransaction) {
        return ResponseEntity.ok(typetransactionService.update(id, typetransaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        typetransactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
