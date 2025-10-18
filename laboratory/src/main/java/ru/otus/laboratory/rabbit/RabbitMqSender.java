package ru.otus.laboratory.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.MeasurementResultDto;
import ru.otus.laboratory.dto.adapter.AdapterTaskDto;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.model.TestTubeStatus;
import ru.otus.laboratory.service.MeasurementResultService;
import ru.otus.laboratory.service.TestTubeResultService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMqSender {

    private static final String LIS_TO_ADAPTER_ROUTE_KEY = "lis.to.adapter.route.key";

    private final RabbitTemplate rabbitTemplate;

    private final MeasurementResultService measurementResultService;

    private final TestTubeResultService testTubeResultService;


    public void sendToAdapterMeasurementByBarcode(String barcode) {
        var measurementResultList = measurementResultService.findByTestTubeResultBarcode(barcode);

        if (measurementResultList == null || measurementResultList.isEmpty()) {
            throw new NotFoundException("Not found measurement result by barcode %s".formatted(barcode));
        }

        List<String> extcodeList = new ArrayList<>();

        for (MeasurementResultDto measurementResult : measurementResultList) {
            extcodeList.add(measurementResult.getMeasurementItem().getExtcode());
        }

        AdapterTaskDto adapterTaskDto = new AdapterTaskDto();
        adapterTaskDto.setBarcode(barcode);
        adapterTaskDto.setExtcodes(extcodeList);

        log.info("SENT %s".formatted(adapterTaskDto));
        rabbitTemplate.convertAndSend(LIS_TO_ADAPTER_ROUTE_KEY, adapterTaskDto);

        testTubeResultService.updateStatusByBarcode(barcode, null, TestTubeStatus.IN_WORK);
    }
}
