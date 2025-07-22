package ru.otus.laboratory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.laboratory.dto.PatientCreateDto;
import ru.otus.laboratory.dto.PatientDto;
import ru.otus.laboratory.dto.PatientUpdateDto;
import ru.otus.laboratory.service.PatientService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("REST Контроллер для пациента")
@WebMvcTest(PatientControllerRest.class)
public class PatientControllerRestTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private PatientService patientService;

    public static Stream<Arguments> createTestPatientNameIncorrect() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("name"),
                Arguments.of("NAME"),
                Arguments.of("nameE"),
                Arguments.of(" name"),
                Arguments.of("nam e"),
                Arguments.of("ИМЯ"),
                Arguments.of("имя"),
                Arguments.of("и Мя")
        );
    }

    public static Stream<Arguments> createTestPatientBirthdayIncorrect() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("1986-1-1"),
                Arguments.of("1986/3/1"),
                Arguments.of("01-01-1985")
        );
    }

    @DisplayName("должен отдать пациента по имени")
    @Test
    public void shouldReturnPatientByName() throws Exception {
        List<PatientDto> returnedPatientList = createReturnedPatientList();

        when(patientService.findByName(any())).thenReturn(returnedPatientList);

        mvc.perform(get("/api/patient/Test"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(returnedPatientList)));
    }

    private List<PatientDto> createReturnedPatientList() {
        return Stream.of(
                new PatientDto(1L, "Test", "Test", "Test",
                        "1979-01-01", "+7(900)-00-000-00", "test@test.ru")
        ).toList();
    }

    @DisplayName("должен создать пациента")
    @Test
    public void shouldCreatePatient() throws Exception {
        PatientCreateDto createDto = new PatientCreateDto("Create", "Create", "Create",
                "1979-02-20", "+7(900)458-85-85", null);

        PatientDto savedPatientDto = new PatientDto(10L, "Create", "Create", "Create",
                "1979-02-20", "+7(900)458-85-85", null);

        when(patientService.create(createDto)).thenReturn(savedPatientDto);

        String expectedResult = mapper.writeValueAsString(createDto);

        mvc.perform(post("/api/patient")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(savedPatientDto)));
    }

    @DisplayName("должен при создании пациента вернуть ошибку, когда не корректно firstName")
    @ParameterizedTest(name = "= {0}")
    @MethodSource("createTestPatientNameIncorrect")
    public void shouldReturnErrorIfPatientMidNameIncorrectForCreate(String name) throws Exception {
        PatientCreateDto createDto = new PatientCreateDto("Create", name, "Create",
                "1979-02-20", "+7(900)458-85-85", null);

        String expectedResult = mapper.writeValueAsString(createDto);

        mvc.perform(post("/api/patient")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("должен при создании пациента вернуть ошибку, когда не корректно lastName")
    @ParameterizedTest(name = "= {0}")
    @MethodSource("createTestPatientNameIncorrect")
    public void shouldReturnErrorIfPatientLastNameIncorrectForCreate(String name) throws Exception {
        PatientCreateDto createDto = new PatientCreateDto("Create", "Create", name,
                "1979-02-20", "+7(900)458-85-85", null);

        String expectedResult = mapper.writeValueAsString(createDto);

        mvc.perform(post("/api/patient")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest());
    }


    @DisplayName("должен при создании пациента вернуть ошибку, когда не корректно midName")
    @ParameterizedTest(name = "= {0}")
    @MethodSource("createTestPatientNameIncorrect")
    public void shouldReturnErrorIfPatientFirstNameIncorrectForCreate(String name) throws Exception {
        PatientCreateDto createDto = new PatientCreateDto(name, "Create", "Create",
                "1979-02-20", "+7(900)458-85-85", null);

        String expectedResult = mapper.writeValueAsString(createDto);

        mvc.perform(post("/api/patient")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest());
    }


    @DisplayName("должен при создании пациента вернуть ошибку, когда не корректно birthday")
    @ParameterizedTest(name = "= {0}")
    @MethodSource("createTestPatientBirthdayIncorrect")
    public void shouldReturnErrorIfPatientBirthdayIncorrectForCreate(String birthday) throws Exception {
        PatientCreateDto createDto = new PatientCreateDto("Create", "Create", "Create",
                birthday, "+7(900)458-85-85", null);

        String expectedResult = mapper.writeValueAsString(createDto);

        mvc.perform(post("/api/patient")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("должен обновить пациента")
    @Test
    public void shouldUpdatePatient() throws Exception {
        PatientUpdateDto updateDto = new PatientUpdateDto(1L, "Fname", "Mname", "Lname",
                "1979-02-20", "+7(900)458-85-85", null);

        PatientDto savedPatientDto = new PatientDto(1L, "Fname", "Mname", "Lname",
                "1979-02-20", "+7(900)458-85-85", null);

        when(patientService.update(updateDto)).thenReturn(savedPatientDto);

        String expectedResult = mapper.writeValueAsString(updateDto);

        mvc.perform(put("/api/patient")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(savedPatientDto)));
    }


    @DisplayName("должен при обновлении пациента вернуть ошибку, когда не корректно firstName")
    @ParameterizedTest(name = "= {0}")
    @MethodSource("createTestPatientNameIncorrect")
    public void shouldReturnErrorIfPatientMidNameIncorrectForUpdate(String name) throws Exception {
        PatientUpdateDto updateDto = new PatientUpdateDto(1L, name, "Mname", "Lname",
                "1979-02-20", "+7(900)458-85-85", null);

        String expectedResult = mapper.writeValueAsString(updateDto);

        mvc.perform(put("/api/patient")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("должен при обновлении пациента вернуть ошибку, когда не корректно lastName")
    @ParameterizedTest(name = "= {0}")
    @MethodSource("createTestPatientNameIncorrect")
    public void shouldReturnErrorIfPatientLastNameIncorrectForUpdate(String name) throws Exception {
        PatientUpdateDto updateDto = new PatientUpdateDto(1L, "Fname", "Mname", name,
                "1979-02-20", "+7(900)458-85-85", null);

        String expectedResult = mapper.writeValueAsString(updateDto);

        mvc.perform(put("/api/patient")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest());
    }


    @DisplayName("должен при обновлении пациента вернуть ошибку, когда не корректно midName")
    @ParameterizedTest(name = "= {0}")
    @MethodSource("createTestPatientNameIncorrect")
    public void shouldReturnErrorIfPatientFirstNameIncorrectForUpdate(String name) throws Exception {
        PatientUpdateDto updateDto = new PatientUpdateDto(1L, "Fname", name, "Lname",
                "1979-02-20", "+7(900)458-85-85", null);

        String expectedResult = mapper.writeValueAsString(updateDto);

        mvc.perform(put("/api/patient")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest());
    }


    @DisplayName("должен при обновлении пациента вернуть ошибку, когда не корректно birthday")
    @ParameterizedTest(name = "= {0}")
    @MethodSource("createTestPatientBirthdayIncorrect")
    public void shouldReturnErrorIfPatientBirthdayIncorrectForUpdate(String birthday) throws Exception {
        PatientUpdateDto updateDto = new PatientUpdateDto(1L, "Fname", "Mname", "Lname",
                birthday, "+7(900)458-85-85", null);

        String expectedResult = mapper.writeValueAsString(updateDto);

        mvc.perform(put("/api/patient")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest());
    }
}
