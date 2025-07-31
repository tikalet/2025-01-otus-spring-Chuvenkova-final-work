package ru.otus.laboratory.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.laboratory.controller.TestTubeControllerRest;
import ru.otus.laboratory.dto.TestTubeResultErrorUpdateDto;
import ru.otus.laboratory.model.Authority;
import ru.otus.laboratory.rabbit.RabbitMqSender;
import ru.otus.laboratory.service.TestTubeErrorService;
import ru.otus.laboratory.service.TestTubeResultService;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@DisplayName("Безопасность. REST Контроллер для тары")
@WebMvcTest(controllers = TestTubeControllerRest.class)
public class TestTubeControllerRestSecurityTest extends ControllerRestSecurityTest {

    @MockitoBean
    private TestTubeResultService testTubeResultService;

    @MockitoBean
    private TestTubeErrorService testTubeErrorService;

    @MockitoBean
    private RabbitMqSender rabbitMqSender;

    public static Stream<Arguments> createTestDataForError() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403),
                Arguments.of("test", List.of(new Authority("NURSE"), new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("DOCTOR"), new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("LAB_ASSISTANT"), new Authority("LABORATORY")), 200)
        );
    }

    public static Stream<Arguments> createTestDataForTestTubeResultBy() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403),
                Arguments.of("test", List.of(new Authority("NURSE"), new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("DOCTOR"), new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("LAB_ASSISTANT"), new Authority("LABORATORY")), 200)
        );
    }

    public static Stream<Arguments> createTestDataForLabAssistant() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 403),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403),
                Arguments.of("test", List.of(new Authority("NURSE"), new Authority("LABORATORY")), 403),
                Arguments.of("test", List.of(new Authority("DOCTOR"), new Authority("LABORATORY")), 403),
                Arguments.of("test", List.of(new Authority("LAB_ASSISTANT"), new Authority("LABORATORY")), 200)
        );
    }

    @DisplayName("должен проверить доступ для метода GET /api/testTubeError статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForError")
    void shouldReturnErrorList(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/testTubeError");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода GET /api/testTubeResult/id/{id} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForTestTubeResultBy")
    void shouldReturnTestTubeResultById(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/testTubeResult/id/1");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода GET /api/testTubeResult/barcode/{barcode} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForTestTubeResultBy")
    void shouldReturnTestTubeResultByBarcode(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/testTubeResult/barcode/0000000001");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода GET /api/testTubeResult/time/{time}/status/{status} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForTestTubeResultBy")
    void shouldReturnTestTubeResultByTimeAndStatus(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/testTubeResult/time/2025-05-02/status/1");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода GET /api/testTubeResult/status/{status} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForTestTubeResultBy")
    void shouldReturnTestTubeResultByStatus(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/testTubeResult/status/1");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода PUT /api/testTubeResult/error статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForTestTubeResultBy")
    void shouldUpdateTestTubeResultError(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        TestTubeResultErrorUpdateDto errorUpdateDto = new TestTubeResultErrorUpdateDto(1L, 1);
        String expectedDto = mapper.writeValueAsString(errorUpdateDto);

        var request = MockMvcRequestBuilders.put("/api/testTubeResult/error")
                .contentType(APPLICATION_JSON)
                .content(expectedDto);
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода PUT /api/labAss/testTubeResult/{barcode} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForLabAssistant")
    void shouldUpdateForLabAssistant(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.put("/api/labAss/testTubeResult/000000001");
        checkStatusAndRedirect(user, authorityList, status, request);
    }
}
