package com.example.TrainingSprintBoot.repository;

import com.example.TrainingSprintBoot.modele.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNom(String nom);
}
