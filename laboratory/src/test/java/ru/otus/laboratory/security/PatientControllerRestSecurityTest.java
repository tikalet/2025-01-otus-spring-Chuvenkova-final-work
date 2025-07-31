package ru.otus.laboratory.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.laboratory.controller.PatientControllerRest;
import ru.otus.laboratory.dto.PatientCreateDto;
import ru.otus.laboratory.dto.PatientUpdateDto;
import ru.otus.laboratory.model.Authority;
import ru.otus.laboratory.service.PatientService;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@DisplayName("Безопасность. REST Контроллер для пациента")
@WebMvcTest(controllers = PatientControllerRest.class)
public class PatientControllerRestSecurityTest extends ControllerRestSecurityTest {

    @MockitoBean
    private PatientService patientService;

    public static Stream<Arguments> createTestDataForPatientByName() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403)
        );
    }

    public static Stream<Arguments> createTestDataForCreatePatient() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 201),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403)
        );
    }

    public static Stream<Arguments> createTestDataForUpdatePatient() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403)
        );
    }

    public static Stream<Arguments> createTestDataForDeletePatient() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 204),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403)
        );
    }

    @DisplayName("должен проверить доступ для метода GET /api/patient/{name} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForPatientByName")
    void shouldReturnPatientByName(String user, List<GrantedAuthority> authorityList, int status) throws Exception {

        var request = MockMvcRequestBuilders.get("/api/patient/Test");

        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода POST /api/patient статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForCreatePatient")
    void shouldCreatePatient(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        PatientCreateDto createDto = new PatientCreateDto("Create", "Create", "Create",
                "1979-02-20", "+7(900)458-85-85", null);

        String expectedResult = mapper.writeValueAsString(createDto);

        var request = MockMvcRequestBuilders.post("/api/patient")
                .contentType(APPLICATION_JSON)
                .content(expectedResult);

        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода PUT /api/patient статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForUpdatePatient")
    void shouldUpdatePatient(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        PatientUpdateDto updateDto = new PatientUpdateDto(1L, "Fname", "Mname", "Lname",
                "1979-02-20", "+7(900)458-85-85", null);

        String expectedResult = mapper.writeValueAsString(updateDto);

        var request = MockMvcRequestBuilders.put("/api/patient")
                .contentType(APPLICATION_JSON)
                .content(expectedResult);

        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода DELETE /api/patient/{id} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForDeletePatient")
    void shouldDeletePatient(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.delete("/api/patient/1");

        checkStatusAndRedirect(user, authorityList, status, request);
    }

}
