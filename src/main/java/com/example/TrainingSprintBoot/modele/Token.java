package com.example.TrainingSprintBoot.modele;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    private boolean revoked;
    private boolean expired;
    private LocalDateTime createdAt;
    private LocalDateTime logoutAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employe employe;

}
