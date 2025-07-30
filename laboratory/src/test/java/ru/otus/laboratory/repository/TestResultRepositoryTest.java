package ru.otus.laboratory.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.otus.laboratory.model.TestResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Репозиторий результатов тестов должен проверить синтаксис")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestResultRepositoryTest {

    @Autowired
    private TestResultRepository testResultRepository;

    @DisplayName("create")
    @Test
    void shouldCheckCreate() {
        TestResult testResult = new TestResult();
        testResult.setTestTubeResultId(1);
        testResult.setStaffId(1);
        testResult.setOrderResultId(1);
        testResult.setPrice(100);
        testResult.setTestItemId(1);
        testResult.setStatusId(1);

        assertThatCode(() -> testResultRepository.create(testResult)).doesNotThrowAnyException();
        assertThat(testResult.getId()).isNotZero();
    }

    @DisplayName("updateStatusByOrderId")
    @Test
    void shouldCheckUpdateStatusByOrderId() {
        assertThatCode(() -> testResultRepository.updateStatusByOrderId(1L, 2)).doesNotThrowAnyException();
    }

    @DisplayName("updateStatusByTestTubeResultId")
    @Test
    void shouldCheckUpdateStatusByTestTubeResultId() {
        assertThatCode(() -> testResultRepository.updateStatusByTestTubeResultId(1L, 2)).doesNotThrowAnyException();
    }

    @DisplayName("findByTestTubeResultId")
    @Test
    void shouldCheckFindByTestTubeResultId() {
        assertThatCode(() -> testResultRepository.findByTestTubeResultId(1L)).doesNotThrowAnyException();
    }

    @DisplayName("findByOrderId")
    @Test
    void shouldCheckFindByOrderId() {
        assertThatCode(() -> testResultRepository.findByOrderId(1L)).doesNotThrowAnyException();
    }

}
