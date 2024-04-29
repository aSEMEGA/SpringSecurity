package com.example.TrainingSprintBoot.security.Service;

import com.example.TrainingSprintBoot.Exception.ExceptionNotfound;
import com.example.TrainingSprintBoot.enums.State;
import com.example.TrainingSprintBoot.modele.Employe;
import com.example.TrainingSprintBoot.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeRepository employeRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employe employe = employeRepository.findByEmailAndState(email, State.Activate);
        if(employe == null){
           throw new ExceptionNotfound("Email ou mot de passe incorrect");
        }
        return UserDetailsImpl.build(employe);
    }
}
