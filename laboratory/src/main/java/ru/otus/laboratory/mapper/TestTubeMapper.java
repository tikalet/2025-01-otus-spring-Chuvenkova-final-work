package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.model.TestTubeItem;

@Component
public class TestTubeMapper {


    public TestTubeItemDto fromModel(TestTubeItem testTubeItem) {
        TestTubeItemDto testTubeItemDto = new TestTubeItemDto();
        testTubeItemDto.setId(testTubeItem.getId());
        testTubeItemDto.setName(testTubeItem.getName());
        return testTubeItemDto;
    }

    public TestTubeItem toModel(TestTubeItemDto testTubeItemDto) {
        TestTubeItem testTubeItem = new TestTubeItem();
        testTubeItem.setId(testTubeItemDto.getId());
        testTubeItem.setName(testTubeItemDto.getName());
        return testTubeItem;
    }
}
