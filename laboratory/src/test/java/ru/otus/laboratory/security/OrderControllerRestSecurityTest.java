package ru.otus.laboratory.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.laboratory.controller.OrderControllerRest;
import ru.otus.laboratory.dto.OrderResultCreateDto;
import ru.otus.laboratory.model.Authority;
import ru.otus.laboratory.service.OrderService;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@DisplayName("Безопасность. REST Контроллер для заказов")
@WebMvcTest(controllers = OrderControllerRest.class)
public class OrderControllerRestSecurityTest extends ControllerRestSecurityTest {

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    public static Stream<Arguments> createTestDataForOrderByPatient() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403),
                Arguments.of("test", List.of(new Authority("NURSE"), new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("DOCTOR"), new Authority("LABORATORY")), 200)
        );
    }

    public static Stream<Arguments> createTestDataForCreateOrder() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 201),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403),
                Arguments.of("test", List.of(new Authority("NURSE"), new Authority("LABORATORY")), 201),
                Arguments.of("test", List.of(new Authority("DOCTOR"), new Authority("LABORATORY")), 201)
        );
    }

    public static Stream<Arguments> createTestDataForNurseOrder() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 403),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403),
                Arguments.of("test", List.of(new Authority("NURSE"), new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("DOCTOR"), new Authority("LABORATORY")), 403)
        );
    }

    public static Stream<Arguments> createTestDataForOrderForDoctorByBarcode() {
        return Stream.of(
                Arguments.of(null, null, 401),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 403),
                Arguments.of("test", List.of(new Authority("")), 403),
                Arguments.of("test", List.of(new Authority("PATIENT")), 403),
                Arguments.of("test", List.of(new Authority("NURSE"), new Authority("LABORATORY")), 403),
                Arguments.of("test", List.of(new Authority("DOCTOR"), new Authority("LABORATORY")), 200)
        );
    }

    @DisplayName("должен проверить доступ для метода GET /api/order/patient/{patientId} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForOrderByPatient")
    void shouldReturnOrderByPatient(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/order/patient/1");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода POST /api/order статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForCreateOrder")
    void shouldCreateOrder(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        OrderResultCreateDto orderResultCreateDto = new OrderResultCreateDto(1L, 2L, List.of(1L));
        String expectedResult = mapper.writeValueAsString(orderResultCreateDto);

        var request = MockMvcRequestBuilders.post("/api/order")
                .contentType(APPLICATION_JSON)
                .content(expectedResult);
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода GET /api/nurse/order статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForNurseOrder")
    void shouldReturnOrderForNurse(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/nurse/order");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода GET /api/nurse/order/{id} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForNurseOrder")
    void shouldReturnOrderForNurseById(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/nurse/order/1");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода PUT /api/nurse/order/{id} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForNurseOrder")
    void shouldUpdateOrderForNurseById(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.put("/api/nurse/order/1");
        checkStatusAndRedirect(user, authorityList, status, request);
    }

    @DisplayName("должен проверить доступ для метода PUT /api/doctor/order/barcode/{barcode} статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestDataForOrderForDoctorByBarcode")
    void shouldUpdateOrderForDoctorByBarcode(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.put("/api/doctor/order/barcode/000000002");
        checkStatusAndRedirect(user, authorityList, status, request);
    }
}
