package com.example.TrainingSprintBoot.service;

import com.example.TrainingSprintBoot.Exception.ExceptionNotfound;
import com.example.TrainingSprintBoot.dto.AuthRequest;
import com.example.TrainingSprintBoot.dto.AuthResponse;
import com.example.TrainingSprintBoot.enums.State;
import com.example.TrainingSprintBoot.modele.Employe;
import com.example.TrainingSprintBoot.modele.Role;
import com.example.TrainingSprintBoot.modele.Token;
import com.example.TrainingSprintBoot.repository.EmployeRepository;
import com.example.TrainingSprintBoot.repository.RoleRepository;
import com.example.TrainingSprintBoot.security.Jwt.JwtService;
import com.example.TrainingSprintBoot.security.Service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final EmployeRepository employeRepository;
    private final RoleRepository roleRepository;

    private final JwtService jwtService;
    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            List<Role> roleList = new ArrayList<>();
            List<String> roleNames = Arrays.asList("SUPER_ADMIN", "ADMIN", "EMPLOYEE", "CASHIER", "COUNTER_CLERK");

            for (String authority : roles) {
                if (roleNames.contains(authority)) {
                    roleList.add(getRoleByName(authority));
                }
            }

            tokenService.revokeAllEmployeeTokens(this.getAuthor());
            Token savedToken = tokenService.save(this.getAuthor(), token);

            return new AuthResponse(
                    userDetails.getId(),
                    userDetails.getNom(),
                    userDetails.getPrenom(),
                    userDetails.getAdresse(),
                    userDetails.getEmail(),
                    roleList,
                    userDetails.getState(),
                    token
            );
        } catch (BadCredentialsException e) {
            throw new ExceptionNotfound("Email ou mot de passe incorrect");
        }
    }
    Role getRoleByName(String nom) {
        Role role = roleRepository.findByNom(nom);
        if (role == null) {
            throw new ExceptionNotfound("Role incorrect");
        }
        return role;
    }
    public Employe getAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return employeRepository.findByEmailAndState(email, State.Activate);
        }
        return null;
    }
}
