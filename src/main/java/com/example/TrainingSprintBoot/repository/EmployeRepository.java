package com.example.TrainingSprintBoot.repository;

import com.example.TrainingSprintBoot.enums.State;
import com.example.TrainingSprintBoot.modele.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeRepository extends JpaRepository<Employe, Long> {

    Employe findByEmailAndState(String email, State state);

    Employe findByIdAndState(Long id, State state);

    List<Employe> findByState(State state);

}
