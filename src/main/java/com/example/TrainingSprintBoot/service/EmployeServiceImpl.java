package com.example.TrainingSprintBoot.service;

import com.example.TrainingSprintBoot.Exception.ExceptionNotfound;
import com.example.TrainingSprintBoot.dto.EmployeResponse;
import com.example.TrainingSprintBoot.enums.State;
import com.example.TrainingSprintBoot.modele.Employe;
import com.example.TrainingSprintBoot.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements EmployeService {

    private  final EmployeRepository employeRepository;
    @Override
    public EmployeResponse save(Employe employe) {
        Employe foundEmploye = employeRepository.save(employe);
        return mapToResponse(foundEmploye);
    }

    @Override
    public EmployeResponse update(Employe employe) {
        Employe toUpdate = employeRepository.findByIdAndState(employe.getId(), State.Activate);

            if(toUpdate == null){
                throw new ExceptionNotfound("Aucun employe trouver");
            }
            toUpdate.setNom(employe.getNom());
            toUpdate.setPrenom(employe.getPrenom());
            toUpdate.setAdress(employe.getAdress());


        return mapToResponse(toUpdate);
    }

    @Override
    public EmployeResponse findbyIdAndState(Long id, State state) {
        Employe employeFind = employeRepository.findByIdAndState(id, state);
        if(employeFind == null){
            throw new ExceptionNotfound("Aucun employe trouver avec l'id :" +id);
        }
        return mapToResponse(employeFind);
    }

    @Override
    public List<EmployeResponse> read() {
        List<Employe> employes = employeRepository.findAll();
        List<EmployeResponse> employeesDto = new ArrayList<>();
        for (Employe employe : employes){
            employeesDto.add(mapToResponse(employe));
        }
        return mapToResponse(employes);
    }

    @Override
    public List<EmployeResponse> findByState(State state){
        List<Employe> employeList = employeRepository.findByState(State.Delete);
        List<EmployeResponse> employeResponseList = new ArrayList<>();
        for(Employe employe : employeList){
            employeResponseList.add(mapToResponse(employe));
        }

        return mapToResponse(employeList);
    }
    @Override
    public EmployeResponse delete(Employe employe) {

        Employe employe1 = employeRepository.findByIdAndState(employe.getId(), State.Activate);
        if(employe1 == null){
            throw new ExceptionNotfound("Employe n'existe pas");
        }

        employe1.setState(State.Delete);
        return mapToResponse(employe1);
    }

    EmployeResponse mapToResponse(Employe employe){
        return EmployeResponse.builder()
                        .id(employe.getId())
                .prenom(employe.getPrenom())
                .adress(employe.getAdress())
                .nom(employe.getNom())
                .state(employe.getState())
                        .build();
    }

    List<EmployeResponse> mapToResponse(List<Employe> employes){
        List<EmployeResponse> employeResponses = new ArrayList<>();
        for (Employe employe : employes) {
            employeResponses.add(mapToResponse(employe));
        }
        return employeResponses;
    }
}
