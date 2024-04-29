package com.example.TrainingSprintBoot.modele;

import com.example.TrainingSprintBoot.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Enumerated(EnumType.STRING)
    private State state = State.Activate;
    @ManyToMany
    private List<Employe> employes = new ArrayList<>();

    public Role(String nom) {
        this.nom = nom;
    }
}
