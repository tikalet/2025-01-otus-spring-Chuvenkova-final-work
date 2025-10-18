package ru.otus.laboratory.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.otus.laboratory.config.PostgresTestContainerInitializer;
import ru.otus.laboratory.dto.PatientCreateDto;
import ru.otus.laboratory.dto.PatientUpdateDto;
import ru.otus.laboratory.mapper.PatientMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@ContextConfiguration(initializers = {PostgresTestContainerInitializer.class})
@TestPropertySource(properties = "my.property=value1")
@DisplayName("Сервис пациентов")
@MybatisTest
@Import({PatientServiceImpl.class, PatientMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientServiceTest {

    @Autowired
    private PatientServiceImpl patientService;

    @DisplayName("должен отдать пациента по имени")
    @Test
    void shouldReturnCorrectPatientById() {
        assertThatCode(() -> patientService.findByName("TEST")).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен сохранить пациента")
    @Test
    void shouldCreatePatient() {
        PatientCreateDto createDto = new PatientCreateDto("Service", "Service", "Service",
                "1979-02-20", "+7(900)458-85-85", null);

        assertThatCode(() -> patientService.create(createDto)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен обновить пациента")
    @Test
    void shouldCUpdatePatient() {
        PatientUpdateDto updateDto = new PatientUpdateDto(1L, "Test", "Test", "Test",
                "2020-20-20", "+7(900)-999-99-99", null);
        assertThatCode(() -> patientService.update(updateDto)).doesNotThrowAnyExceptionExcept();
    }

}
