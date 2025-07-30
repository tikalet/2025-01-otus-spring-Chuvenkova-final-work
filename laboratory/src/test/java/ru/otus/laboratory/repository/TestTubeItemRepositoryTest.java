package ru.otus.laboratory.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Репозиторий тары должен проверить синтаксис")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestTubeItemRepositoryTest {

    @Autowired
    private TestTubeItemRepository testTubeItemRepository;

    @DisplayName("findAll")
    @Test
    void shouldCheckAllTestItem() {
        assertThatCode(() -> testTubeItemRepository.findAll()).doesNotThrowAnyException();
    }

    @DisplayName("findById")
    @Test
    void shouldCheckFindById() {
        assertThatCode(() -> testTubeItemRepository.findById(1)).doesNotThrowAnyException();
    }
}
