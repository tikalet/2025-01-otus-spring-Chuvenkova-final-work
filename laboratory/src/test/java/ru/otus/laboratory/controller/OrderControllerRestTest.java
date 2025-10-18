package ru.otus.laboratory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.laboratory.dto.OrderResultCreateDto;
import ru.otus.laboratory.dto.OrderResultDto;
import ru.otus.laboratory.dto.OrderResultNurseDto;
import ru.otus.laboratory.dto.PatientDto;
import ru.otus.laboratory.dto.StaffDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.security.CustomUserDetailsService;
import ru.otus.laboratory.service.OrderService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("REST Контроллер для заказа")
@WebMvcTest(controllers = OrderControllerRest.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class OrderControllerRestTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    @DisplayName("должен отдать заказы для пациента")
    @Test
    public void shouldReturnOrderByPatientId() throws Exception {
        List<OrderResultDto> orderResultDtos = createReturnedOrderResultList();

        when(orderService.findOrderByPatientId(any())).thenReturn(orderResultDtos);

        mvc.perform(get("/api/order/patient/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderResultDtos)));
    }

    private List<OrderResultDto> createReturnedOrderResultList() {
        return Stream.of(
                new OrderResultDto(1L, 100, "Status", "2025-07-30T15:31:43.911+03:00",
                        new PatientDto(1L, "Test", "Test", "Test",
                                "1979-01-01", "+7(900)-00-000-00", "test@test.ru"),
                        new StaffDto(1L, "Staff", "Staff", "Staff", "position"),
                        null)
        ).toList();
    }

    @DisplayName("должен создать заказ")
    @Test
    public void shouldCreate() throws Exception {
        OrderResultCreateDto orderResultCreateDto = new OrderResultCreateDto(1L, 2L, List.of(1L));

        OrderResultDto savedOrderResultDto = new OrderResultDto(1L, 100, "Status",
                "2025-07-30T15:31:43.911+03:00",
                new PatientDto(1L, "Test", "Test", "Test",
                        "1979-01-01", "+7(900)-00-000-00", "test@test.ru"),
                new StaffDto(1L, "Staff", "Staff", "Staff", "position"),
                null);

        when(orderService.create(orderResultCreateDto)).thenReturn(savedOrderResultDto);

        String expectedResult = mapper.writeValueAsString(orderResultCreateDto);

        mvc.perform(post("/api/order")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(savedOrderResultDto)));
    }

    @DisplayName("должен отдать заказы для медсестры")
    @Test
    public void shouldReturnActualOrderForNurse() throws Exception {
        List<OrderResultNurseDto> orderResultDtos = createReturnedOrderResultNurseList();

        when(orderService.findOrderForNurse()).thenReturn(orderResultDtos);

        mvc.perform(get("/api/nurse/order"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderResultDtos)));
    }

    @DisplayName("должен отдать информацию по одному заказу для медсестры")
    @Test
    public void shouldReturnActualOrderByIdForNurse() throws Exception {
        OrderResultNurseDto orderResultDto = createReturnedOrderResultNurseList().get(0);

        when(orderService.findOrderForNurseById(1L)).thenReturn(orderResultDto);

        mvc.perform(get("/api/nurse/order/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(orderResultDto)));
    }

    private List<OrderResultNurseDto> createReturnedOrderResultNurseList() {
        return Stream.of(
                new OrderResultNurseDto(1L, "Patient patient patient",
                        List.of(new TestTubeResultNurseDto(1L, "123", "test_tube_name")))
        ).toList();
    }
}
