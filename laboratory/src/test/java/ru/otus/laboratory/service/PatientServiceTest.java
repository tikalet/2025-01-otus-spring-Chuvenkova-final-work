package ru.otus.laboratory.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.dto.PatientCreateDto;
import ru.otus.laboratory.dto.PatientUpdateDto;
import ru.otus.laboratory.mapper.PatientMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Сервис пациентов должен")
@MybatisTest
@Import({PatientServiceImpl.class, PatientMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NEVER)
public class PatientServiceTest {

    @Autowired
    private PatientServiceImpl patientService;

    @DisplayName("должен загрузить пациента по имени")
    @Test
    void shouldReturnCorrectPatientById() {
        assertThatCode(() -> patientService.findByName(any())).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен сохранить пациента")
    @Test
    void shouldCreatePatient() {
        PatientCreateDto createDto = new PatientCreateDto("F", "M", "L",
                "1979-02-20", "+7(900)458-85-85", null);
        assertThatCode(() -> patientService.create(createDto)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен обновить пациента")
    @Test
    void shouldCUpdatePatient() {
        PatientUpdateDto updateDto = new PatientUpdateDto(1L, "F", "M", "L",
                "1979-02-20", "+7(900)458-85-85", null);
        assertThatCode(() -> patientService.update(updateDto)).doesNotThrowAnyExceptionExcept();
    }

}
