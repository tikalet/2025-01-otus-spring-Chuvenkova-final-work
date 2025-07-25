package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestResultOrderDto;
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
        testItemDto.setTestTubeId(testItem.getTestTubeId());
        return testItemDto;
    }

    public TestItem toModel(TestItemDto testItemDto) {
        TestItem testItem = new TestItem();
        testItem.setId(testItemDto.getId());
        testItem.setName(testItemDto.getName());
        testItem.setPrice(testItemDto.getPrice());
        return testItem;
    }

    public TestResultOrderDto fromModel(TestResult testResult, TestItemDto testItemDto,
                                        TestTubeResultOrderDto testTubeResultOrderDto,
                                        String status) {
        TestResultOrderDto testResultOrderDto = new TestResultOrderDto();
        testResultOrderDto.setId(testResult.getId());
        testResultOrderDto.setPrice(testResult.getPrice());
        testResultOrderDto.setTestItem(testItemDto);
        testResultOrderDto.setOrderResultId(testResult.getOrderResultId());
        testResultOrderDto.setStatus(status);
        testResultOrderDto.setStaffId(testResult.getStaffId());
        testResultOrderDto.setTestTubeResult(testTubeResultOrderDto);
        return testResultOrderDto;
    }

    public TestResultDto fromModel(TestResult testResult, String testItemName, String status) {
        TestResultDto testResultOrderDto = new TestResultDto();
        testResultOrderDto.setId(testResult.getId());
        testResultOrderDto.setTestItem(testItemName);
        testResultOrderDto.setOrderResultId(testResult.getOrderResultId());
        testResultOrderDto.setStatus(status);
        return testResultOrderDto;
    }
}
