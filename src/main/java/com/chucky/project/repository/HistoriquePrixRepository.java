package com.chucky.project.repository;

import com.chucky.project.model.HistoriquePrix;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriquePrixRepository extends JpaRepository<HistoriquePrix,Integer> {

    @Query("SELECT h FROM HistoriquePrix h WHERE h.cryptomonnaieId = :cryptoId ORDER BY h.dateEnregistrement DESC")
    Optional<HistoriquePrix> findLatestByCryptomonnaieId(@Param("cryptoId") Integer cryptoId);

}
