package com.chucky.project.repository;

import com.chucky.project.model.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByNom(String nom);
}
