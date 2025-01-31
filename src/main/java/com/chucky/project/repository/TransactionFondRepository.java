package com.chucky.project.repository;

import com.chucky.project.model.TransactionFond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionFondRepository extends JpaRepository<TransactionFond,Integer> {

}
