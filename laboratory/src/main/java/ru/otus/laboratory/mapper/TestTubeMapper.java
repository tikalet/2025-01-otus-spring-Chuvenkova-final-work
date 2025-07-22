package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.TestTubeErrorDto;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.model.TestTubeItem;
import ru.otus.laboratory.model.TestTubeResult;

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

    public TestTubeResultDto fromModel(TestTubeResult testTubeResult,
                                       TestTubeItemDto testTubeItemDto,
                                       TestTubeErrorDto testTubeErrorDto) {

        TestTubeResultDto testTubeResultDto = new TestTubeResultDto();
        testTubeResultDto.setTestTubeItem(testTubeItemDto);
        testTubeResultDto.setId(testTubeResult.getId());
        testTubeResultDto.setStatusId(testTubeResult.getStatusId());
        testTubeResultDto.setBarcode(testTubeResult.getBarcode());
        testTubeResultDto.setTestTubeError(testTubeErrorDto);
        return testTubeResultDto;
    }
}
