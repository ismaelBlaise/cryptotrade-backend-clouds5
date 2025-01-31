package com.chucky.project.repository;

import com.chucky.project.model.Cryptomonnaie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptomonnaieRepository extends JpaRepository<Cryptomonnaie,Integer> {

}
