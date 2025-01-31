package com.chucky.project.controller;

import com.chucky.project.model.Commission;
import com.chucky.project.service.CommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commissions")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

    @PostMapping
    public Commission save(@RequestBody Commission commission) {
        return commissionService.save(commission);
    }

    @GetMapping
    public List<Commission> findAll() {
        return commissionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commission> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(commissionService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commission> update(@PathVariable Integer id, @RequestBody Commission commission) {
        return ResponseEntity.ok(commissionService.update(id, commission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        commissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
