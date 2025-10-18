package ru.otus.laboratory.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.laboratory.config.PostgresTestContainerInitializer;
import ru.otus.laboratory.model.TestTubeResult;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@ContextConfiguration(initializers = {PostgresTestContainerInitializer.class})
@DisplayName("Репозиторий результатов тары должен проверить синтаксис")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestTubeResultRepositoryTest {

    @Autowired
    private TestTubeResultRepository testTubeResultRepository;

    @DisplayName("create")
    @Test
    void shouldCheckCreate() {
        TestTubeResult testTubeResult = new TestTubeResult();
        testTubeResult.setStatusId(1);
        testTubeResult.setTestTubeErrorId(null);
        testTubeResult.setOrderResultId(1L);
        testTubeResult.setTestTubeItemId(1);
        testTubeResult.setTakeTestTime(Instant.now().toString());
        testTubeResult.setBarcode("test_barcode");
        testTubeResult.setDisposalTime(Instant.now().toString());

        assertThatCode(() -> testTubeResultRepository.create(testTubeResult)).doesNotThrowAnyException();
        assertThat(testTubeResult.getId()).isNotZero();
    }

    @DisplayName("update")
    @Test
    void shouldCheckUpdate() {
        TestTubeResult testTubeResult = new TestTubeResult();
        testTubeResult.setStatusId(1);
        testTubeResult.setTestTubeErrorId(null);
        testTubeResult.setOrderResultId(1L);
        testTubeResult.setTestTubeItemId(1);
        testTubeResult.setTakeTestTime(Instant.now().toString());
        testTubeResult.setBarcode("test_barcode");
        testTubeResult.setDisposalTime(Instant.now().toString());
        testTubeResult.setId(1);

        assertThatCode(() -> testTubeResultRepository.update(testTubeResult)).doesNotThrowAnyException();
    }


    @DisplayName("updateStatus")
    @Test
    void shouldCheckUpdateStatus() {
        assertThatCode(() -> testTubeResultRepository.updateStatus(1, 2)).doesNotThrowAnyException();
    }

    @DisplayName("findByParam")
    @Test
    void shouldCheckFindByParam() {
        assertThatCode(() -> testTubeResultRepository.findByParam(null, "")).doesNotThrowAnyException();
    }

    @DisplayName("findPatientByBarcode")
    @Test
    void shouldCheckFindPatientByBarcode() {
        assertThatCode(() -> testTubeResultRepository.findPatientByBarcode("233313313")).doesNotThrowAnyException();
    }
}
