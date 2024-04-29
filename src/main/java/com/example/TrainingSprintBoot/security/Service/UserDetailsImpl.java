package com.example.TrainingSprintBoot.security.Service;

import com.example.TrainingSprintBoot.enums.State;
import com.example.TrainingSprintBoot.modele.Employe;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter

public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String password;
    private State state;
    private Collection<?extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String nom, String prenom, String adresse, String email, String password, State state, Collection<?extends GrantedAuthority> authorities){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.password = password;
        this.state = state;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Employe employe){
        List<GrantedAuthority> roles = employe.getRoles().stream()
                .map(appRole -> new SimpleGrantedAuthority(appRole.getNom()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                employe.getId(),
                employe.getNom(),
                employe.getPrenom(),
                employe.getAdress(),
                employe.getEmail(),
                employe.getPassword(),
                employe.getState(),
                roles
        );
    }

    public static UserDetailsImpl build(Long id, String nom, String prenom,String adresse, String email,

                                       String password, State state,
                                        Collection<? extends GrantedAuthority> roles) {
        return new UserDetailsImpl(id, nom, prenom, adresse, email,
                 password, state, roles);
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
