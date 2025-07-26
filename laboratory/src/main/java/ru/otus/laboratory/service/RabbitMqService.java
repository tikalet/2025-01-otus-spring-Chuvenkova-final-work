package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.adapter.AdapterTaskDto;
import ru.otus.laboratory.mapper.AdapterMapper;
import ru.otus.laboratory.model.MeasurementResult;
import ru.otus.laboratory.repository.MeasurementResultRepository;
import ru.otus.laboratory.repository.TestTubeResultRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RabbitMqService {

    private static final String LIS_EXCHANGE = "lis-exchange";

    private static final String LIS_TO_ADAPTER_ROUTE_KEY = "lis-to-adapter-route-key";

    private static final String ADAPTER_TO_LIS_ROUTE_KEY = "adapter-to-lis-route-key";

    private static final String ADAPTER_ERROR_ROUTE_KEY = "adapter-error-route-key";

    private final RabbitTemplate rabbitTemplate;

    private final AdapterMapper adapterMapper;

    private final TestTubeResultRepository testTubeResultRepository;

    private final MeasurementResultRepository measurementResultRepository;

    private final MeasurementItemService measurementItemService;

    public void sendToAdapterMeasurementByBarcode(String barcode) {
        List<Long> testResultIdList = testTubeResultRepository.findTestResultByBarcode(barcode);
        List<MeasurementResult> measurementResultList = measurementResultRepository.findByTestResultIdList(testResultIdList);
        List<String> extcodeList = new ArrayList<>();

        for (MeasurementResult measurementResult : measurementResultList) {
            var measurementItem = measurementItemService.findById(measurementResult.getMeasurementItemId());
            extcodeList.add(measurementItem.getExtcode());
        }

        AdapterTaskDto adapterTaskDto = new AdapterTaskDto();
        adapterTaskDto.setBarcode(barcode);
        adapterTaskDto.setExtcodes(extcodeList);

        rabbitTemplate.setExchange(LIS_EXCHANGE);
        rabbitTemplate.convertAndSend(LIS_TO_ADAPTER_ROUTE_KEY, adapterTaskDto);
    }
}
