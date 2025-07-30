package ru.otus.laboratory.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Репозиторий измерений должен проверить синтаксис")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MeasurementItemRepositoryTest {

    @Autowired
    private MeasurementItemRepository measurementItemRepository;

    @DisplayName("findByTestItemId")
    @Test
    void shouldCheckFindByTestItemId() {
        assertThatCode(() -> measurementItemRepository.findByTestItemId(1)).doesNotThrowAnyException();
    }

    @DisplayName("findById")
    @Test
    void shouldCheckFindById() {
        assertThatCode(() -> measurementItemRepository.findById(1)).doesNotThrowAnyException();
    }
}
