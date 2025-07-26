package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.TestTubeTrack;

import java.util.List;

@Mapper
public interface TestTubeTrackRepository {

    void create(TestTubeTrack testTubeTrack);

    List<TestTubeTrack> findByTestTubeResultId(Long testTubeResultId);
}
