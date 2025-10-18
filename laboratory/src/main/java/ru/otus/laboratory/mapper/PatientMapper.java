package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.PatientCreateDto;
import ru.otus.laboratory.dto.PatientDto;
import ru.otus.laboratory.dto.PatientUpdateDto;
import ru.otus.laboratory.model.Patient;

@Component
public class PatientMapper {

    public PatientDto fromModel(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setBirthday(patient.getBirthday());
        patientDto.setEmail(patient.getEmail());
        patientDto.setPhone(patient.getPhone());
        patientDto.setLastName(patient.getLastName());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setMiddleName(patient.getMiddleName());
        return patientDto;
    }

    public Patient toModel(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setBirthday(patientDto.getBirthday());
        patient.setEmail(patientDto.getEmail());
        patient.setPhone(patientDto.getPhone());
        patient.setLastName(patientDto.getLastName());
        patient.setFirstName(patientDto.getFirstName());
        patient.setMiddleName(patientDto.getMiddleName());
        return patient;
    }

    public Patient toModel(PatientCreateDto patientDto) {
        Patient patient = new Patient();
        patient.setBirthday(patientDto.getBirthday());
        patient.setEmail(patientDto.getEmail());
        patient.setPhone(patientDto.getPhone());
        patient.setLastName(patientDto.getLastName());
        patient.setFirstName(patientDto.getFirstName());
        patient.setMiddleName(patientDto.getMiddleName());
        return patient;
    }

    public Patient toModel(PatientUpdateDto patientDto) {
        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setBirthday(patientDto.getBirthday());
        patient.setEmail(patientDto.getEmail());
        patient.setPhone(patientDto.getPhone());
        patient.setLastName(patientDto.getLastName());
        patient.setFirstName(patientDto.getFirstName());
        patient.setMiddleName(patientDto.getMiddleName());
        return patient;
    }
}
