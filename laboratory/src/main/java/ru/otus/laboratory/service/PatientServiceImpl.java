package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.dto.PatientCreateDto;
import ru.otus.laboratory.dto.PatientDto;
import ru.otus.laboratory.dto.PatientUpdateDto;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.mapper.PatientMapper;
import ru.otus.laboratory.model.Patient;
import ru.otus.laboratory.repository.PatientRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    @Override
    public List<PatientDto> findByName(String searchPattern) {
        List<Patient> patientList = patientRepository.findByName(searchPattern);

        if (patientList == null || patientList.isEmpty()) {
            throw new NotFoundException("Patients with name %s not found".formatted(searchPattern));
        }

        return patientRepository.findByName(searchPattern).stream().map(patientMapper::fromModel).toList();
    }

    @Override
    public PatientDto findById(Long id) {
        return patientMapper.fromModel(getPatient(id));
    }

    @Transactional
    @Override
    public PatientDto create(PatientCreateDto createDto) {
        Patient patient = patientMapper.toModel(createDto);
        fillSearchPattern(patient);
        patientRepository.create(patient);
        return patientMapper.fromModel(patient);
    }

    @Transactional
    @Override
    public PatientDto update(PatientUpdateDto updateDto) {
        Patient patient = getPatient(updateDto.getId());
        patient = patientMapper.toModel(updateDto);
        fillSearchPattern(patient);
        patientRepository.update(patient);
        return patientMapper.fromModel(patient);
    }

    @Override
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

    private Patient getPatient(long id) {
        return patientRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Patient with id %d not found".formatted(id)));
    }

    private void fillSearchPattern(Patient patient) {
        String searchPattern = patient.getFirstName() + " " + patient.getMiddleName() + " " + patient.getLastName();
        patient.setSearchPattern(searchPattern.toUpperCase());
    }
}
