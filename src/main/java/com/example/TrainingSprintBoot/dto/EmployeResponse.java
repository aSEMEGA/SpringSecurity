package com.example.TrainingSprintBoot.dto;

import com.example.TrainingSprintBoot.enums.State;
import lombok.*;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String adress;
    private State state;
}
