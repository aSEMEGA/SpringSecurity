package com.example.TrainingSprintBoot.controller;

import com.example.TrainingSprintBoot.dto.EmployeResponse;
import com.example.TrainingSprintBoot.enums.State;
import com.example.TrainingSprintBoot.modele.Employe;
import com.example.TrainingSprintBoot.service.EmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employe")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;


    @PostMapping("/save")
    ResponseEntity<EmployeResponse> save(@RequestBody Employe employe){
        return new ResponseEntity<>(employeService.save(employe), HttpStatus.CREATED);
    }

    @GetMapping("/read")
    ResponseEntity<List<EmployeResponse>> All(Employe employe){
        return new ResponseEntity<>(employeService.read(), HttpStatus.OK);

    }

    @GetMapping("/read/state")

    ResponseEntity<List<EmployeResponse>> findByState(State state){
        return new ResponseEntity<>(employeService.findByState(state), HttpStatus.OK);
    }

    @GetMapping("/read/{id}/state/{state}")
    ResponseEntity<EmployeResponse> findByIdAndState(@PathVariable Long id, @PathVariable State state) {
        return new ResponseEntity<>(employeService.findbyIdAndState(id, state), HttpStatus.OK);
    }
    @PutMapping("/update")
    ResponseEntity<EmployeResponse> update(@RequestBody Employe employe){
        return new ResponseEntity<>(employeService.update(employe), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    ResponseEntity<EmployeResponse> delete(Employe employe){
        return new ResponseEntity<>(employeService.delete(employe), HttpStatus.OK);
    }
}
