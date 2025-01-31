package com.chucky.project.service;

import com.chucky.project.model.Role;
import com.chucky.project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Introuvable"));
    }

    public Role update(Integer id, Role role) {
        Role existingRole = findById(id);
        role.setId(existingRole.getId());
        return roleRepository.save(role);
    }

    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }
    
}
