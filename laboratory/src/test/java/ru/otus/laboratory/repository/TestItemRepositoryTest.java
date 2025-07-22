package ru.otus.laboratory.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Репозиторий тестов должен")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestItemRepositoryTest {

    @Autowired
    private TestItemRepository testItemRepository;

    @DisplayName("проверить синтаксис findAll")
    @Test
    void shouldCheckAllPatient() {
        assertThatCode(() -> testItemRepository.findAll()).doesNotThrowAnyException();
    }
}
