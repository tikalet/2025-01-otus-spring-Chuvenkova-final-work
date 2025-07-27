package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultErrorUpdateDto;
import ru.otus.laboratory.dto.TestTubeResultNurseDto;
import ru.otus.laboratory.dto.TestTubeResultOrderDto;

import java.util.List;

public interface TestTubeResultService {

    List<TestTubeResultOrderDto> create(Long orderResultId, List<Long> testTubeItemIdList);

    List<TestTubeResultNurseDto> findByOrderId(Long orderId);

    void updateStatus(long orderId, int statusId);

    TestTubeResultDto findById(Long id);

    TestTubeResultDto findByBarcode(String barcode);

    List<TestTubeResultDto> findByTimeAndStatus(String time, Long statusId);

    void recordArrivalTestTubeAtLaboratory(String barcode);

    TestTubeResultDto updateErrorInfo(TestTubeResultErrorUpdateDto testTubeResultErrorUpdateDto);

    void updateErrorInfo(String barcode, Integer errorId);
}
