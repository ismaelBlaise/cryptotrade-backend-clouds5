package com.chucky.project.repository;

import com.chucky.project.model.TypeTransaction;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeTransactionRepository extends JpaRepository<TypeTransaction,Integer> {

    public Optional<TypeTransaction> findByTypeTransaction(String typeTransaction);
    
}
