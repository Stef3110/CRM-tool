package com.example.crm.repository;

import com.example.crm.model.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    Personnel findByUsername(String username);
    List<Personnel> findAllByRole(String role);
}
