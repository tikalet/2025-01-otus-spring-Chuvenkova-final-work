package ru.otus.laboratory.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.laboratory.controller.TestIControllerRest;
import ru.otus.laboratory.model.Authority;
import ru.otus.laboratory.service.TestItemService;

import java.util.List;
import java.util.stream.Stream;

@DisplayName("Безопасность. REST Контроллер для тестов")
@WebMvcTest(controllers = TestIControllerRest.class)
public class TestIControllerRestSecurityTest extends ControllerRestSecurityTest {

    @MockitoBean
    private TestItemService testItemService;

    public static Stream<Arguments> createTestData() {
        return Stream.of(
                Arguments.of(null, null, 200),
                Arguments.of("test", List.of(new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("")), 200),
                Arguments.of("test", List.of(new Authority("PATIENT")), 200),
                Arguments.of("test", List.of(new Authority("NURSE"), new Authority("LABORATORY")), 200),
                Arguments.of("test", List.of(new Authority("DOCTOR"), new Authority("LABORATORY")), 200)
        );
    }

    @DisplayName("должен проверить доступ для метода GET /api/testItem статус")
    @ParameterizedTest(name = "{2} для пользователя {0} с правами {1}")
    @MethodSource("createTestData")
    void shouldReturnTestList(String user, List<GrantedAuthority> authorityList, int status) throws Exception {
        var request = MockMvcRequestBuilders.get("/api/testItem");
        checkStatusAndRedirect(user, authorityList, status, request);
    }
}
