package com.chucky.project.repository;

import com.chucky.project.model.TransactionCrypto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCryptoRepository extends JpaRepository<TransactionCrypto,Integer> {
    Optional<TransactionCrypto> findByValidationToken(String token);
}
