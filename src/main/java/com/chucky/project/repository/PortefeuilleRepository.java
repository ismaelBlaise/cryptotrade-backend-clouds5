package com.chucky.project.repository;

import com.chucky.project.model.Portefeuille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortefeuilleRepository extends JpaRepository<Portefeuille,Integer> {

}
