package ru.otus.laboratory.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import ru.otus.laboratory.converter.MeasurementConverter;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.adapter.AdapterTaskResultDto;
import ru.otus.laboratory.exceptions.BadSearchParamException;
import ru.otus.laboratory.mapper.MeasurementMapper;
import ru.otus.laboratory.util.DateTimeUtil;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Сервис измерений должен")
@MybatisTest
@Import({MeasurementResultServiceImpl.class, MeasurementItemServiceImpl.class, MeasurementMapper.class,
        MeasurementConverter.class, DateTimeUtil.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MeasurementResultServiceImplTest {

    @Autowired
    private MeasurementResultServiceImpl measurementResultService;

    @DisplayName("проверить наличие пустых результатов измерений")
    @Test
    void shouldCheckEmptyValue() {
        assertThatCode(() -> measurementResultService.hasEmptyResult("0000000001"))
                .doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("проверить поиск по ШК")
    @Test
    void shouldFindByTestTubeResultBarcode() {
        assertThatCode(() -> measurementResultService.findByTestTubeResultBarcode("0000000001"))
                .doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("проверить поиск по ШК - происходит падение при пустом ШК")
    @Test
    void shouldErrorFindByTestTubeResultBarcode() {
        assertThatCode(() -> measurementResultService.findByTestTubeResultBarcode(null))
                .isInstanceOf(BadSearchParamException.class);
    }

    @DisplayName("проверить поиск по заказу")
    @Test
    void shouldFindByOrder() {
        assertThatCode(() -> measurementResultService.findByOrderId(1L))
                .doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("проверить поиск по заказу - происходит падение при пустом заказе")
    @Test
    void shouldErrorFindByOrder() {
        assertThatCode(() -> measurementResultService.findByOrderId(null))
                .isInstanceOf(BadSearchParamException.class);
    }

    @DisplayName("проверить поиск по пациенту и измерению")
    @Test
    void shouldFindByPatientIdAndMeasurementItemId() {
        assertThatCode(() -> measurementResultService.findByPatientIdAndMeasurementItemId(1L, 1L))
                .doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("проверить поиск по пациенту и измерению - происходит падение при пустых параметрах")
    @Test
    void shouldErrorFindByPatientIdAndMeasurementItemId() {
        assertThatCode(() -> measurementResultService.findByPatientIdAndMeasurementItemId(null, null))
                .isInstanceOf(BadSearchParamException.class);
    }

    @DisplayName("проверить сохранение")
    @Test
    void shouldCreateMeasurementResult() {
        assertThatCode(() -> measurementResultService.create(
                List.of(new TestResultDto(1L, 1L, "Status",
                        new TestItemDto(2L, "", 1, 1L))),
                1L))
                .doesNotThrowAnyExceptionExcept();
    }


    @DisplayName("проверить обновление")
    @Test
    void shouldUpdateMeasurementResult() {
        assertThatCode(() -> measurementResultService.update("0000000001",
                List.of(new AdapterTaskResultDto("M1", 2.2))))
                .doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("проверить обновление - при несуществующем ExtCode нет падений")
    @Test
    void shouldUpdateMeasurementResultInvalidExtCode() {
        assertThatCode(() -> measurementResultService.update("0000000001",
                List.of(new AdapterTaskResultDto("0000", 2.2))))
                .doesNotThrowAnyExceptionExcept();
    }
}
