package ru.otus.laboratory.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.otus.laboratory.model.TestTubeTrack;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Репозиторий трека тары должен проверить синтаксис")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestTubeTrackRepositoryTest {

    @Autowired
    private TestTubeTrackRepository testTubeTrackRepository;

    @DisplayName("create")
    @Test
    void shouldCheckCreate() {
        TestTubeTrack testTubeTrack = new TestTubeTrack();
        testTubeTrack.setTestTubeErrorId(1);
        testTubeTrack.setStaffId(1L);
        testTubeTrack.setTestTubeResultId(1);
        testTubeTrack.setChangedTime(Instant.now().toString());
        testTubeTrack.setStatusIdOlId(1);
        testTubeTrack.setStatusIdNewId(2);

        assertThatCode(() -> testTubeTrackRepository.create(testTubeTrack)).doesNotThrowAnyException();
        assertThat(testTubeTrack.getId()).isNotZero();
    }

    @DisplayName("findByTestTubeResultId")
    @Test
    void shouldCheckFindByTestTubeResultId() {
        assertThatCode(() -> testTubeTrackRepository.findByTestTubeResultId(1L)).doesNotThrowAnyException();
    }
}
