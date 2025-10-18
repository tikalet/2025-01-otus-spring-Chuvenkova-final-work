package ru.otus.laboratory.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.laboratory.config.PostgresTestContainerInitializer;
import ru.otus.laboratory.model.MeasurementResult;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@ContextConfiguration(initializers = {PostgresTestContainerInitializer.class})
@DisplayName("Репозиторий результатов измерений должен проверить синтаксис")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MeasurementResultRepositoryTest {

    @Autowired
    private MeasurementResultRepository measurementResultRepository;

    @DisplayName("create")
    @Test
    void shouldCheckCreate() {
        MeasurementResult measurementResult = new MeasurementResult();
        measurementResult.setMeasurTime(Instant.now().toString());
        measurementResult.setTestResultId(1);
        measurementResult.setMeasurementItemId(1);
        measurementResult.setValue(1.1);
        measurementResult.setPatientId(1);

        assertThatCode(() -> measurementResultRepository.create(measurementResult)).doesNotThrowAnyException();
        assertThat(measurementResult.getId()).isNotZero();
    }

    @DisplayName("update")
    @Test
    void shouldCheckUpdate() {
        MeasurementResult measurementResult = new MeasurementResult();
        measurementResult.setMeasurTime(Instant.now().toString());
        measurementResult.setTestResultId(1);
        measurementResult.setMeasurementItemId(1);
        measurementResult.setValue(1.1);
        measurementResult.setPatientId(1);
        measurementResult.setId(1);

        assertThatCode(() -> measurementResultRepository.update(measurementResult)).doesNotThrowAnyException();
    }

    @DisplayName("hasEmptyResult")
    @Test
    void shouldCheckHasEmptyResult() {
        assertThatCode(() -> measurementResultRepository.hasEmptyResult("0000000001")).doesNotThrowAnyException();
    }

    @DisplayName("findByParam")
    @Test
    void shouldCheckFindByParam() {
        assertThatCode(() -> measurementResultRepository.findByParam(null, "")).doesNotThrowAnyException();
    }
}
