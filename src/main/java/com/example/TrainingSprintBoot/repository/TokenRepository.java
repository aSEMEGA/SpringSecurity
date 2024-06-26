package com.example.TrainingSprintBoot.repository;

import com.example.TrainingSprintBoot.modele.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllByEmployeIdAndExpiredIsFalseAndRevokedIsFalse(Long id);

    Optional<Token> findByToken(String token);

    Token findByTokenAndRevokedIsFalseOrExpiredIsFalse(String token);
}
