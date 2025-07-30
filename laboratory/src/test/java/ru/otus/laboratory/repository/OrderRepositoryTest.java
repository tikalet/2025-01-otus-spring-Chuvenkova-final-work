package ru.otus.laboratory.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.otus.laboratory.model.OrderResult;
import ru.otus.laboratory.model.Patient;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Репозиторий заказов должен проверить синтаксис")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("create")
    @Test
    void shouldCheckCreate() {
        Patient patient = new Patient();
        patient.setId(1L);

        OrderResult orderResult = new OrderResult();
        orderResult.setStaffId(2L);
        orderResult.setPatient(patient);
        orderResult.setPrice(1000);
        orderResult.setPaymentTime(Instant.now().toString());
        orderResult.setStatusId(1);

        assertThatCode(() -> orderRepository.create(orderResult)).doesNotThrowAnyException();
        assertThat(orderResult.getId()).isNotZero();
    }

    @DisplayName("updateStatus")
    @Test
    void shouldCheckUpdate() {
        assertThatCode(() -> orderRepository.updateStatus(1, 2)).doesNotThrowAnyException();
    }

    @DisplayName("findByParam")
    @Test
    void shouldCheckFindByParam() {
        assertThatCode(() -> orderRepository.findByParam(null, "")).doesNotThrowAnyException();
    }
}
