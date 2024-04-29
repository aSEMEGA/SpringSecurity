package com.example.TrainingSprintBoot.service;

import com.example.TrainingSprintBoot.dto.EmployeResponse;
import com.example.TrainingSprintBoot.enums.State;
import com.example.TrainingSprintBoot.modele.Employe;

import java.util.List;
import java.util.Optional;

public interface EmployeService {

    EmployeResponse save(Employe employe);

    EmployeResponse update(Employe employe);

   EmployeResponse findbyIdAndState(Long id, State state);
    List<EmployeResponse> read();

    EmployeResponse delete(Employe employe);

    List<EmployeResponse> findByState(State state);

}
