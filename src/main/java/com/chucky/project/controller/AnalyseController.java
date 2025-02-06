package com.chucky.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chucky.project.service.CryptomonnaieService;

@Controller
@RequestMapping("/analyse")
public class AnalyseController {
    
    @Autowired
    private CryptomonnaieService cryptomonnaieService;

    @GetMapping()
    public ResponseEntity<?> getAllCrypto() {
        try {
            return ResponseEntity.ok(cryptomonnaieService.findAll());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage()); 
        }
    }

    
}
