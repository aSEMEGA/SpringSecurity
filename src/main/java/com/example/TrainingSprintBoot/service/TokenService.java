package com.example.TrainingSprintBoot.service;

import com.example.TrainingSprintBoot.modele.Employe;
import com.example.TrainingSprintBoot.modele.Token;

public interface TokenService {

    Token save(Employe employe, String token);

    void revokeAllEmployeeTokens(Employe employe);
}
