package ru.otus.laboratory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.laboratory.dto.TestTubeErrorDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultErrorUpdateDto;
import ru.otus.laboratory.rabbit.RabbitMqSender;
import ru.otus.laboratory.service.TestTubeErrorService;
import ru.otus.laboratory.service.TestTubeResultService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("REST Контроллер для тары")
@WebMvcTest(controllers = TestTubeControllerRest.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class TestTubeControllerRestTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private TestTubeResultService testTubeResultService;

    @MockitoBean
    private TestTubeErrorService testTubeErrorService;

    @MockitoBean
    private RabbitMqSender rabbitMqSender;

    @DisplayName("должен список ошибок тары")
    @Test
    public void shouldReturnTestTubeErrorList() throws Exception {
        List<TestTubeErrorDto> testTubeErrorDtos = List.of(new TestTubeErrorDto(1, "Error"));

        when(testTubeErrorService.findAll()).thenReturn(testTubeErrorDtos);

        mvc.perform(get("/api/testTubeError"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(testTubeErrorDtos)));
    }

    @DisplayName("должен отдать список тары по id")
    @Test
    public void shouldReturnById() throws Exception {
        TestTubeResultDto testTubeResultDto = createReturnedTestTubeList().get(0);

        when(testTubeResultService.findById(1L)).thenReturn(testTubeResultDto);

        mvc.perform(get("/api/testTubeResult/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(testTubeResultDto)));
    }

    @DisplayName("должен отдать список тары по barcode")
    @Test
    public void shouldReturnByBarcode() throws Exception {
        TestTubeResultDto testTubeResultDto = createReturnedTestTubeList().get(0);

        when(testTubeResultService.findByBarcode("11111111")).thenReturn(testTubeResultDto);

        mvc.perform(get("/api/testTubeResult/barcode/11111111"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(testTubeResultDto)));
    }

    private List<TestTubeResultDto> createReturnedTestTubeList() {
        return Stream.of(
                new TestTubeResultDto(1L, 1L, "status", "000000002",
                        "tube name", "error", "time", "time 2", null, null)
        ).toList();
    }

    @DisplayName("должен отдать список тары по времени забора и статусу")
    @Test
    public void shouldReturnByTimeAndStatus() throws Exception {
        List<TestTubeResultDto> testTubeResultDtos = createReturnedTestTubeList();

        when(testTubeResultService.findByTimeAndStatus("2025-05-22", 1L)).thenReturn(testTubeResultDtos);

        mvc.perform(get("/api/testTubeResult/time/2025-05-22/status/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(testTubeResultDtos)));
    }

    @DisplayName("должен вернуть ошибку для поиска тары по времени забора и статусу, если время не корректное")
    @Test
    public void shouldReturnErrorByTimeAndStatus() throws Exception {
        mvc.perform(get("/api/testTubeResult/time/2025-05-2/status/1"))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("должен отдать список тары по статусу")
    @Test
    public void shouldReturnByStatus() throws Exception {
        List<TestTubeResultDto> testTubeResultDtos = createReturnedTestTubeList();

        when(testTubeResultService.findByTimeAndStatus(null, 1L)).thenReturn(testTubeResultDtos);

        mvc.perform(get("/api/testTubeResult/status/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(testTubeResultDtos)));
    }

    @DisplayName("должен обновить ошибку тары")
    @Test
    public void shouldUpdateError() throws Exception {
        TestTubeResultErrorUpdateDto errorUpdateDto = new TestTubeResultErrorUpdateDto(1L, 1);
        String expectedDto = mapper.writeValueAsString(errorUpdateDto);

        TestTubeResultDto testTubeResultDto = createReturnedTestTubeList().get(0);
        when(testTubeResultService.updateErrorInfo(errorUpdateDto)).thenReturn(testTubeResultDto);

        mvc.perform(put("/api/testTubeResult/error")
                        .contentType(APPLICATION_JSON)
                        .content(expectedDto))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(testTubeResultDto)));
    }

}
