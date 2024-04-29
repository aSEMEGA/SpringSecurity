package com.example.TrainingSprintBoot.service;

import com.example.TrainingSprintBoot.dto.AuthRequest;
import com.example.TrainingSprintBoot.dto.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    AuthResponse authenticate(AuthRequest authRequest);
}
