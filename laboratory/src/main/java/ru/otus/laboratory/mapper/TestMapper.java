package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;
import ru.otus.laboratory.model.TestItem;
import ru.otus.laboratory.model.TestResult;

@Component
public class TestMapper {

    public TestItemDto fromModel(TestItem testItem) {
        TestItemDto testItemDto = new TestItemDto();
        testItemDto.setId(testItem.getId());
        testItemDto.setName(testItem.getName());
        testItemDto.setPrice(testItem.getPrice());
        return testItemDto;
    }

    public TestItem toModel(TestItemDto testItemDto) {
        TestItem testItem = new TestItem();
        testItem.setId(testItemDto.getId());
        testItem.setName(testItemDto.getName());
        testItem.setPrice(testItemDto.getPrice());
        return testItem;
    }


    public TestResultDto fromModel(TestResult testResult, TestItemDto testItemDto,
                                   TestTubeResultOrderDto testTubeResultOrderDto,
                                   String status) {
        TestResultDto testResultDto = new TestResultDto();
        testResultDto.setId(testResult.getId());
        testResultDto.setPrice(testResult.getPrice());
        testResultDto.setTestItem(testItemDto);
        testResultDto.setOrderResultId(testResult.getOrderResultId());
        testResultDto.setStatus(status);
        testResultDto.setStaffId(testResult.getStaffId());
        testResultDto.setTestTubeResult(testTubeResultOrderDto);
        return testResultDto;
    }
}
