package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.otus.laboratory.model.Patient;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PatientRepository {

    List<Patient> findAll();

    List<Patient> findByName(@Param("searchPattern") String searchPattern);

    Optional<Patient> findById(@Param("id") long id);

    void create(Patient patient);

    void update(Patient patient);

    void deleteById(Long id);
}
