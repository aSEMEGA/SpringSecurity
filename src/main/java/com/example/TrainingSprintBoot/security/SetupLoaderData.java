package com.example.TrainingSprintBoot.security;

import com.example.TrainingSprintBoot.enums.State;
import com.example.TrainingSprintBoot.modele.Employe;
import com.example.TrainingSprintBoot.modele.Role;
import com.example.TrainingSprintBoot.repository.EmployeRepository;
import com.example.TrainingSprintBoot.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RequiredArgsConstructor
@Component
@Transactional
public class SetupLoaderData implements ApplicationListener<ContextRefreshedEvent> {

    private final EmployeRepository employeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private boolean alreadySetup = false;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        final Role superAdminRole = createAppRoleIfNotFound("SUPER_ADMIN");
        final Role adminRole = createAppRoleIfNotFound("ADMIN");
        final Role employeeRole = createAppRoleIfNotFound("EMPLOYEE");
        final List<Role> adminRoles = new ArrayList<>(Arrays.asList(
                superAdminRole, adminRole,employeeRole));
        createEmployeeIfNotFound(adminRoles);
        alreadySetup = true;
    }

    void createEmployeeIfNotFound(List<Role> appRole) {
        Employe employe = employeRepository.findByEmailAndState("test@gmail.com", State.Activate);
        if (employe == null) {
            employe = new Employe();
            employe.setNom("Admin");
            employe.setEmail("test@gmail.com");
            employe.setPassword(passwordEncoder.encode("azert12"));

        }
        employe.setRoles(appRole);
        employeRepository.save(employe);
    }

    Role createAppRoleIfNotFound(final String nom) {
        Role appRole = roleRepository.findByNom(nom);
        if (appRole == null) {
            appRole = new Role(nom);
            appRole = roleRepository.save(appRole);
        }
        return appRole;
    }
}
