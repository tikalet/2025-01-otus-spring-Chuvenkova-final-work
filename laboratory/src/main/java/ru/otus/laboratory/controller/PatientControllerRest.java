package ru.otus.laboratory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.laboratory.dto.PatientCreateDto;
import ru.otus.laboratory.dto.PatientDto;
import ru.otus.laboratory.dto.PatientUpdateDto;
import ru.otus.laboratory.service.PatientService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PatientControllerRest {

    private final PatientService patientService;

    @GetMapping("/api/patient/{name}")
    public ResponseEntity<List<PatientDto>> getPatientByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(patientService.findByName(name), HttpStatus.OK);
    }

    @PostMapping("/api/patient")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientCreateDto patientCreateDto) {
        var patientDto = patientService.create(patientCreateDto);
        return new ResponseEntity<PatientDto>(patientDto, HttpStatus.CREATED);
    }

    @PutMapping("/api/patient")
    public ResponseEntity<PatientDto> updatePatient(@Valid @RequestBody PatientUpdateDto patientUpdateDto) {
        var patientDto = patientService.update(patientUpdateDto);
        return ResponseEntity.ok(patientDto);
    }

    @DeleteMapping("/api/patient/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        patientService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
