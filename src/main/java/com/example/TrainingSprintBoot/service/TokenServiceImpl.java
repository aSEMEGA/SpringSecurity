package com.example.TrainingSprintBoot.service;

import com.example.TrainingSprintBoot.modele.Employe;
import com.example.TrainingSprintBoot.modele.Token;
import com.example.TrainingSprintBoot.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    @Override
    public Token save(Employe employe, String token) {

        Token savedToken = Token.builder()
                .token(token)
                .revoked(false)
                .expired(false)
                .createdAt(LocalDateTime.now())
                .employe(employe)
                .build();
        return tokenRepository.save(savedToken);
    }

    @Override
    public void revokeAllEmployeeTokens(Employe employe) {
        List<Token> tokens = tokenRepository.findAllByEmployeIdAndExpiredIsFalseAndRevokedIsFalse(employe.getId());
        if (tokens.isEmpty()) {
            return;
        }
        tokens.forEach(token -> {
            if (token.getEmploye().equals(employe)) {
                token.setRevoked(true);
                token.setExpired(true);
                token.setLogoutAt(LocalDateTime.now());
            }
        });
        tokenRepository.saveAll(tokens);
    }


}
