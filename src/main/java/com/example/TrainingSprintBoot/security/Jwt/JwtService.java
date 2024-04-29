package com.example.TrainingSprintBoot.security.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.TrainingSprintBoot.Exception.TokenExpiredException;
import com.example.TrainingSprintBoot.security.Service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;
@Service
public class JwtService {

    private static final long EXPIRATION_TIME = 60 * 60 * 24 * 1000;
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    //permet recuperer le username dans le token
    public String extractUsername(String token) {
        return extractClaim(token, Claim::asString);
    }

    //permet d'extracter le contenu du token
    public <T> T extractClaim(String token, Function<Claim, T> claimsResolver) {
        final DecodedJWT decodedJWT = JWT.decode(token);
        final Claim claim = decodedJWT.getClaim("sub");
        return claimsResolver.apply(claim);
    }

    //permet de generer le token

    public String generateJwtToken(Authentication authentication) {
        String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();

        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuer("SprintSecurity")
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    //permet de verifier le token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    //permet de verifier si le token est expiré
    private boolean isTokenExpired(String token) {
        try {
            // Decoder le token
            final DecodedJWT decodedJWT = JWT.decode(token);

            // Récupérer la date expiration du token depuis le token decoder
            final Date expirationDate = decodedJWT.getExpiresAt();

            // Check if the token has expired
            if (expirationDate != null && expirationDate.before(new Date())) {
                // Si le Token est expiré, lance TokenExpiredException
                throw new TokenExpiredException("Le token d'authentification a expiré.");
            }

            // Si le Token est expiré ou non, renvoie true
            return false;
        } catch (TokenExpiredException e) {
            log.error("Token: " + e.getMessage());
            return true;
        }
    }

}
