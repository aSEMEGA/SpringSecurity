package com.example.TrainingSprintBoot.dto;

import com.example.TrainingSprintBoot.enums.State;
import com.example.TrainingSprintBoot.modele.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
   List<Role> roles;
    private State state;
    private String token;
}
