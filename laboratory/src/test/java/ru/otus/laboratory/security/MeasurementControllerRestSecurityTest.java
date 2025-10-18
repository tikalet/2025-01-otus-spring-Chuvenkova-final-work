package ru.otus.laboratory.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.laboratory.controller.MeasurementControllerRest;
import ru.otus.laboratory.model.Authority;
import ru.otus.laboratory.service.MeasurementResultService;

import java.util.List;
import java.util.stream.Stream;

@DisplayName("Безопасность. REST Контроллер для измерений")
@WebMvcTest(controllers = MeasurementControllerRest.class)
public class MeasurementControllerRestSecurityTest extends ControllerRestSecurityTest {

    @MockitoBean
    private MeasurementResultService measurementResultService;

    public static Stream<Arguments> createTestDataForMeasurResult() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403),
                Arguments.of("test", List.of(new Authority("NURSE"), new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("DOCTOR"), new Authority("LABORATORY")), 200)
        );
    }

    @DisplayName("должен проверить доступ для метода GET /api/measurementResult/testTubeResult/{barcode} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForMeasurResult")
    void shouldReturnMeasurResultByBarcode(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/measurementResult/testTubeResult/000000001");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода GET /api/measurementResult/order/{id} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForMeasurResult")
    void shouldReturnMeasurResultByOrder(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/measurementResult/order/1");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода GET " +
            "/api/measurementResult/patient/{patientId}/measurementItemId/{measurementItemId} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForMeasurResult")
    void shouldReturnMeasurResultByPatientAndMeasurItem(String user, List<GrantedAuthority> authorityList,
                                                        int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/measurementResult/patient/1/measurementItemId/1");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

}
