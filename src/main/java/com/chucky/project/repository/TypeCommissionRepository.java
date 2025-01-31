package com.chucky.project.repository;

import com.chucky.project.model.TypeCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeCommissionRepository extends JpaRepository<TypeCommission,Integer> {

}
