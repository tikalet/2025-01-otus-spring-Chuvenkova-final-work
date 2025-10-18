package ru.otus.laboratory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.laboratory.dto.MeasurementItemDto;
import ru.otus.laboratory.dto.MeasurementResultDto;
import ru.otus.laboratory.service.MeasurementResultService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("REST Контроллер для результатов измерений")
@WebMvcTest(controllers = MeasurementControllerRest.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class MeasurementControllerRestTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private MeasurementResultService measurementResultService;

    @DisplayName("должен отдать список измерений по ШК")
    @Test
    public void shouldReturnMeasurementResultByBarcode() throws Exception {
        List<MeasurementResultDto> measurementResultDtos = createReturnedPatientList();

        when(measurementResultService.findByTestTubeResultBarcode(any())).thenReturn(measurementResultDtos);

        mvc.perform(get("/api/measurementResult/testTubeResult/123"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(measurementResultDtos)));
    }

    private List<MeasurementResultDto> createReturnedPatientList() {
        return Stream.of(
                new MeasurementResultDto(1, 1,
                        new MeasurementItemDto(1, "measurement_Item", "unit", 1.0, 2.0, "code"),
                        2.2, "2025-07-30T15:00:00.000+03:00")
        ).toList();
    }

    @DisplayName("должен отдать список измерений для заказа")
    @Test
    public void shouldReturnMeasurementResultByOrder() throws Exception {
        List<MeasurementResultDto> measurementResultDtos = createReturnedPatientList();

        when(measurementResultService.findByOrderId(any())).thenReturn(measurementResultDtos);

        mvc.perform(get("/api/measurementResult/order/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(measurementResultDtos)));
    }

    @DisplayName("должен отдать динамику измерения для пациента и типа измерения")
    @Test
    public void shouldReturnMeasurementResultByMeasurementResultByPatientAndItem() throws Exception {
        List<MeasurementResultDto> measurementResultDtos = createReturnedPatientList();

        when(measurementResultService.findByPatientIdAndMeasurementItemId(1L, 1L)).thenReturn(measurementResultDtos);

        mvc.perform(get("/api/measurementResult/patient/1/measurementItemId/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(measurementResultDtos)));
    }

}
