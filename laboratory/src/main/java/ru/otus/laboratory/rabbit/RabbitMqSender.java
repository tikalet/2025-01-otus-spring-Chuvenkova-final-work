package ru.otus.laboratory.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.adapter.AdapterTaskDto;
import ru.otus.laboratory.model.MeasurementResult;
import ru.otus.laboratory.model.TestTubeStatus;
import ru.otus.laboratory.repository.MeasurementResultRepository;
import ru.otus.laboratory.service.MeasurementItemService;
import ru.otus.laboratory.service.TestTubeResultService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RabbitMqSender {


    private static final String LIS_TO_ADAPTER_ROUTE_KEY = "lis.to.adapter.route.key";

    private final RabbitTemplate rabbitTemplate;

    private final MeasurementResultRepository measurementResultRepository;

    private final MeasurementItemService measurementItemService;

    private final TestTubeResultService testTubeResultService;

    public void sendToAdapterMeasurementByBarcode(String barcode) {
        var testResultIdList = testTubeResultService.findTestResultByBarcode(barcode);
        var measurementResultList = measurementResultRepository.findByTestResultIdList(testResultIdList);
        List<String> extcodeList = new ArrayList<>();

        for (MeasurementResult measurementResult : measurementResultList) {
            var measurementItem = measurementItemService.findById(measurementResult.getMeasurementItemId());
            extcodeList.add(measurementItem.getExtcode());
        }

        AdapterTaskDto adapterTaskDto = new AdapterTaskDto();
        adapterTaskDto.setBarcode(barcode);
        adapterTaskDto.setExtcodes(extcodeList);

        rabbitTemplate.convertAndSend(LIS_TO_ADAPTER_ROUTE_KEY, adapterTaskDto);

        testTubeResultService.updateStatusByBarcode(barcode, TestTubeStatus.IN_WORK);
    }
}
