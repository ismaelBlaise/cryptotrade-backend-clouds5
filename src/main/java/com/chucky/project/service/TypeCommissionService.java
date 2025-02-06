package com.chucky.project.service;

import com.chucky.project.model.TypeCommission;
import com.chucky.project.repository.TypeCommissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TypeCommissionService {

    @Autowired
    private TypeCommissionRepository typecommissionRepository;

    public TypeCommission save(TypeCommission typecommission) {
        return typecommissionRepository.save(typecommission);
    }

    public List<TypeCommission> findAll() {
        return typecommissionRepository.findAll();
    }

    public TypeCommission findById(Integer id) {
        return typecommissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public TypeCommission update(Integer id, TypeCommission typecommission) {
        TypeCommission existingTypeCommission = findById(id);
        typecommission.setId(existingTypeCommission.getId());
        return typecommissionRepository.save(typecommission);
    }

    public void delete(Integer id) {
        typecommissionRepository.deleteById(id);
    }
    
}
