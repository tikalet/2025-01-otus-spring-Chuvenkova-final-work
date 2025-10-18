package ru.otus.laboratory.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.laboratory.config.PostgresTestContainerInitializer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@ContextConfiguration(initializers = {PostgresTestContainerInitializer.class})
@DisplayName("Репозиторий сотрудников должен проверить синтаксис")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StaffRepositoryTest {

    @Autowired
    private StaffRepository staffRepository;

    @DisplayName("findById")
    @Test
    void shouldCheckFindById() {
        assertThatCode(() -> staffRepository.findById(1L)).doesNotThrowAnyException();
    }

    @DisplayName("findByLogin")
    @Test
    void shouldCheckFindByLogin() {
        assertThatCode(() -> staffRepository.findByLogin("cons")).doesNotThrowAnyException();
    }
}
