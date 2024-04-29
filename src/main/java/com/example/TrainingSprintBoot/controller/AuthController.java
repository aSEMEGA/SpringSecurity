package com.example.TrainingSprintBoot.controller;

import com.example.TrainingSprintBoot.dto.AuthRequest;
import com.example.TrainingSprintBoot.dto.AuthResponse;
import com.example.TrainingSprintBoot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private  final AuthService authService;

    @PostMapping
    AuthResponse authenticate(@RequestBody AuthRequest authRequest){
        return authService.authenticate(authRequest);
    }
}
