package ru.otus.laboratory.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.StaffDto;
import ru.otus.laboratory.dto.TestResultDto;
import ru.otus.laboratory.dto.TestTubeErrorDto;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;
import ru.otus.laboratory.dto.TestTubeTrackDto;
import ru.otus.laboratory.model.TestTubeError;
import ru.otus.laboratory.model.TestTubeItem;
import ru.otus.laboratory.model.TestTubeResult;
import ru.otus.laboratory.model.TestTubeStatus;
import ru.otus.laboratory.model.TestTubeTrack;
import ru.otus.laboratory.service.DictService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TestTubeMapper {

    private final DictService dictService;

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

    public TestTubeResultDto fromModel(TestTubeResult testTubeResult,
                                       String testTubeItem,
                                       String status,
                                       String error,
                                       List<TestResultDto> testResultList,
                                       List<TestTubeTrackDto> testTubeTrackDtos) {

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
        testTubeResultDto.setTestTubeTrackList(testTubeTrackDtos);
        return testTubeResultDto;
    }

    public TestTubeErrorDto fromModel(TestTubeError testTubeError) {
        TestTubeErrorDto testTubeErrorDto = new TestTubeErrorDto();
        testTubeErrorDto.setId(testTubeError.getId());
        testTubeErrorDto.setText(testTubeError.getText());
        return testTubeErrorDto;
    }

    public TestTubeTrackDto fromModel(TestTubeTrack testTubeTrack, StaffDto staffDto) {
        TestTubeError error = dictService.findTestTubeErrorById(testTubeTrack.getTestTubeErrorId());
        TestTubeStatus statusOld = dictService.findTestTubeStatusById(testTubeTrack.getStatusIdOlId());
        TestTubeStatus statusNew = dictService.findTestTubeStatusById(testTubeTrack.getStatusIdNewId());

        TestTubeTrackDto testTubeTrackDto = new TestTubeTrackDto();
        testTubeTrackDto.setTestTubeResultId(testTubeTrack.getTestTubeResultId());
        testTubeTrackDto.setId(testTubeTrack.getId());
        testTubeTrackDto.setTestTubeError(error != null ? error.getText() : null);
        testTubeTrackDto.setChangedTime(testTubeTrack.getChangedTime());
        testTubeTrackDto.setStatusOld(statusOld != null ? statusOld.getName() : null);
        testTubeTrackDto.setStatusNew(statusNew != null ? statusNew.getName() : null);
        testTubeTrackDto.setStaff(staffDto);
        return testTubeTrackDto;
    }
}
