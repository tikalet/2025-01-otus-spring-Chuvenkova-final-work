package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.PatientCreateDto;
import ru.otus.laboratory.dto.PatientDto;
import ru.otus.laboratory.dto.PatientUpdateDto;

import java.util.List;

public interface PatientService {

    List<PatientDto> findByName(String searchPattern);

    PatientDto findById(Long id);

    PatientDto create(PatientCreateDto createDto);

    PatientDto update(PatientUpdateDto updateDto);

    void deleteById(Long id);
}
