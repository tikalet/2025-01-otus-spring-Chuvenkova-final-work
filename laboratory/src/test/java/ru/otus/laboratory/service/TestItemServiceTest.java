package ru.otus.laboratory.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import ru.otus.laboratory.mapper.TestMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Сервис тестов должен")
@MybatisTest
@Import({TestItemServiceImpl.class, TestMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestItemServiceTest {

    @Autowired
    private TestItemServiceImpl testItemService;

    @DisplayName("отдать список тестов")
    @Test
    void shouldReturnAllTest() {
        assertThatCode(() -> testItemService.findAll()).doesNotThrowAnyExceptionExcept();
    }
}
