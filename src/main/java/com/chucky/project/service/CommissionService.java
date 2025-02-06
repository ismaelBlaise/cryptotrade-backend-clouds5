package com.chucky.project.service;

import com.chucky.project.model.Commission;
import com.chucky.project.repository.CommissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CommissionService {

    @Autowired
    private CommissionRepository commissionRepository;

    public Commission save(Commission commission) {
        return commissionRepository.save(commission);
    }

    public List<Commission> findAll() {
        return commissionRepository.findAll();
    }

    public Commission findById(Integer id) {
        return commissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public Commission update(Integer id, Commission commission) {
        Commission existingCommission = findById(id);
        commission.setId(existingCommission.getId());
        return commissionRepository.save(commission);
    }

    public void delete(Integer id) {
        commissionRepository.deleteById(id);
    }
    
}
