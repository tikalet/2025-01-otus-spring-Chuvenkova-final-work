package ru.otus.laboratory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.service.TestItemService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("REST Контроллер для тестов")
@WebMvcTest(TestItemControllerRest.class)
public class TestItemControllerRestTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private TestItemService testItemService;

    @DisplayName("должен отдать список тестов")
    @Test
    public void shouldReturnPatientByName() throws Exception {
        List<TestItemDto> returnedList = createReturnedTestItemList();

        when(testItemService.findAll()).thenReturn(returnedList);

        mvc.perform(get("/api/testItem"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(returnedList)));
    }

    private List<TestItemDto> createReturnedTestItemList() {
        return Stream.of(
                new TestItemDto(1L, "Test item", 100)
        ).toList();
    }
}
