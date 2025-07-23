package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.TestTubeErrorDto;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;
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

    public TestTubeResultOrderDto fromModel(TestTubeResult testTubeResult,
                                            TestTubeItemDto testTubeItemDto,
                                            TestTubeErrorDto testTubeErrorDto) {

        TestTubeResultOrderDto testTubeResultOrderDto = new TestTubeResultOrderDto();
        testTubeResultOrderDto.setTestTubeItem(testTubeItemDto);
        testTubeResultOrderDto.setId(testTubeResult.getId());
        testTubeResultOrderDto.setStatusId(testTubeResult.getStatusId());
        testTubeResultOrderDto.setBarcode(testTubeResult.getBarcode());
        testTubeResultOrderDto.setTestTubeError(testTubeErrorDto);
        return testTubeResultOrderDto;
    }

    public TestTubeResultNurseDto fromModel(TestTubeResult testTubeResult, String testTubeName) {
        TestTubeResultNurseDto testTubeResultNurseDto = new TestTubeResultNurseDto();
        testTubeResultNurseDto.setTestTubeName(testTubeName);
        testTubeResultNurseDto.setId(testTubeResult.getId());
        testTubeResultNurseDto.setBarcode(testTubeResult.getBarcode());
        return testTubeResultNurseDto;
    }

    // TODO add status name and track
    public TestTubeResultDto fromModel(TestTubeResult testTubeResult,
                                       TestTubeItemDto testTubeItemDto,
                                       TestTubeErrorDto testTubeErrorDto,
                                       String track) {

        TestTubeResultDto testTubeResultDto = new TestTubeResultDto();
        testTubeResultDto.setTestTubeItem(testTubeItemDto);
        testTubeResultDto.setId(testTubeResult.getId());
//        testTubeResultDto.setStatusId(testTubeResult.getStatusId());
        testTubeResultDto.setBarcode(testTubeResult.getBarcode());
        testTubeResultDto.setTestTubeError(testTubeErrorDto.getName());
        testTubeResultDto.setTakeTestTime(testTubeResult.getTakeTestTime());
        testTubeResultDto.setDisposalTime(testTubeResult.getDisposalTime());
        return testTubeResultDto;
    }
}
