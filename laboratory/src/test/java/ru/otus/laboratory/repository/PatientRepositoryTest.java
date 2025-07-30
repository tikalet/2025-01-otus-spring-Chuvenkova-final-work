package ru.otus.laboratory.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.otus.laboratory.model.Patient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Репозиторий пациентов должен")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientRepositoryTest {


    @Autowired
    private PatientRepository patientRepository;

    @DisplayName("проверить синтаксис findAll")
    @Test
    void shouldCheckAllPatient() {
        assertThatCode(() -> patientRepository.findAll()).doesNotThrowAnyException();
    }

    @DisplayName("проверить поиск по ID")
    @Test
    void shouldFindById() {
        Patient expectedPatient = new Patient(1L, "Test", "Test", "Test",
                "1979-01-01", "+7(900)-00-000-00", "test@test.ru", "TEST TEST TEST");

        var returnedPatient = patientRepository.findById(1L);

        assertThat(returnedPatient).isNotNull().isNotEmpty();
        assertThat(returnedPatient.get()).isEqualTo(expectedPatient);
    }

    @DisplayName("проверить поиск по имени")
    @Test
    void shouldFindByName() {
        Patient expectedPatient = new Patient(1L, "Test", "Test", "Test",
                "1979-01-01", "+7(900)-00-000-00", "test@test.ru", "TEST TEST TEST");

        var returnedPatientList = patientRepository.findByName("TEST TEST TEST");

        assertThat(returnedPatientList).isNotNull().isNotEmpty().hasSize(1);
        assertThat(returnedPatientList.get(0)).isEqualTo(expectedPatient);
    }

    @DisplayName("проверить сохранение пациента")
    @Test
    void shouldCreatePatient() {
        Patient patient = new Patient(null, "first", "mid", "last",
                "1979-01-01", "+7(900)-00-000-00", null, "FIRST MID LAST");

        patientRepository.create(patient);

        assertThat(patient).isNotNull()
                .matches(p -> p.getId() > 0);
    }

    @DisplayName("проверить обновление пациента")
    @Test
    void shouldUpdatePatient() {
        Patient expectedPatient = new Patient(1L, "Test", "Test", "Test",
                "1979-01-01", "+7(900)-00-111-11", "test1979@test.ru", "TEST TEST TEST");

        var beforeUpdatePatient = patientRepository.findById(1L);
        assertThat(beforeUpdatePatient).isNotEqualTo(expectedPatient);

        patientRepository.update(expectedPatient);

        var afterUpdatePatient = patientRepository.findById(1L);
        assertThat(afterUpdatePatient.get()).isEqualTo(expectedPatient);
    }


    @DisplayName("проверить удаление пациента")
    @Test
    void shouldDeletePatient() {
        var deletedPatientId = 2L;

        var beforePatient = patientRepository.findById(deletedPatientId);
        assertThat(beforePatient).isNotNull();

        patientRepository.deleteById(deletedPatientId);

        var afterPatient = patientRepository.findById(deletedPatientId);
        assertThat(afterPatient).isEmpty();
    }

}
