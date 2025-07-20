package ru.otus.laboratory.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.laboratory.mapper.TestItemMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Сервис тестов должен")
@MybatisTest
@Import({TestItemServiceImpl.class, TestItemMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NEVER)
public class TestItemServiceTest {

    @Autowired
    private TestItemServiceImpl testItemService;

    @DisplayName("отдать список тестов")
    @Test
    void shouldReturnCorrectPatientById() {
        assertThatCode(() -> testItemService.findAll()).doesNotThrowAnyExceptionExcept();
    }
}
