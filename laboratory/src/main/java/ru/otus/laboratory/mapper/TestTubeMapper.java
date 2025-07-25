package ru.otus.laboratory.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestTubeErrorDto;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;
import ru.otus.laboratory.model.TestTubeError;
import ru.otus.laboratory.model.TestTubeItem;
import ru.otus.laboratory.model.TestTubeResult;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TestTubeMapper {

    public TestTubeItemDto fromModel(TestTubeItem testTubeItem) {
        TestTubeItemDto testTubeItemDto = new TestTubeItemDto();
        testTubeItemDto.setId(testTubeItem.getId());
        testTubeItemDto.setName(testTubeItem.getName());
        return testTubeItemDto;
    }

    public TestTubeResultOrderDto fromModel(TestTubeResult testTubeResult,
                                            TestTubeItemDto testTubeItemDto,
                                            String status, String error) {

        TestTubeResultOrderDto testTubeResultOrderDto = new TestTubeResultOrderDto();
        testTubeResultOrderDto.setTestTubeItem(testTubeItemDto);
        testTubeResultOrderDto.setId(testTubeResult.getId());
        testTubeResultOrderDto.setStatus(status);
        testTubeResultOrderDto.setBarcode(testTubeResult.getBarcode());
        testTubeResultOrderDto.setError(error);
        return testTubeResultOrderDto;
    }

    public TestTubeResultNurseDto fromModel(TestTubeResult testTubeResult, String testTubeName) {
        TestTubeResultNurseDto testTubeResultNurseDto = new TestTubeResultNurseDto();
        testTubeResultNurseDto.setTestTubeName(testTubeName);
        testTubeResultNurseDto.setId(testTubeResult.getId());
        testTubeResultNurseDto.setBarcode(testTubeResult.getBarcode());
        return testTubeResultNurseDto;
    }

    // TODO add track
    public TestTubeResultDto fromModel(TestTubeResult testTubeResult,
                                       String testTubeItem,
                                       String status,
                                       String error,
                                       List<TestResultDto> testResultList,
                                       String track) {

        TestTubeResultDto testTubeResultDto = new TestTubeResultDto();
        testTubeResultDto.setTestTubeItem(testTubeItem);
        testTubeResultDto.setId(testTubeResult.getId());
        testTubeResultDto.setStatus(status);
        testTubeResultDto.setBarcode(testTubeResult.getBarcode());
        testTubeResultDto.setError(error);
        testTubeResultDto.setTakeTestTime(testTubeResult.getTakeTestTime());
        testTubeResultDto.setDisposalTime(testTubeResult.getDisposalTime());
        testTubeResultDto.setOrderResultId(testTubeResult.getOrderResultId());
        testTubeResultDto.setTestResultList(testResultList);
        return testTubeResultDto;
    }

    public TestTubeErrorDto fromModel(TestTubeError testTubeError) {
        TestTubeErrorDto testTubeErrorDto = new TestTubeErrorDto();
        testTubeErrorDto.setId(testTubeError.getId());
        testTubeErrorDto.setText(testTubeError.getText());
        return testTubeErrorDto;
    }
}
