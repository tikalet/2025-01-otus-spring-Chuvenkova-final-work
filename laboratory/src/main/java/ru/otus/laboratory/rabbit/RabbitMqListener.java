package ru.otus.laboratory.rabbit;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.config.RabbitMqConfig;
import ru.otus.laboratory.dto.adapter.AdapterErrorDto;
import ru.otus.laboratory.dto.adapter.AdapterTaskResponseDto;
import ru.otus.laboratory.repository.TestTubeResultRepository;
import ru.otus.laboratory.service.MeasurementResultService;
import ru.otus.laboratory.service.TestTubeResultService;

@RequiredArgsConstructor
@Service
public class RabbitMqListener {

    private final MeasurementResultService measurementResultService;

    private final TestTubeResultService testTubeResultService;

    private final TestTubeResultRepository testTubeResultRepository;

    @RabbitListener(queues = RabbitMqConfig.ADAPTER_TO_LIS_QUEUE, ackMode = "MANUAL")
    public void processAdapterToLisMessage(AdapterTaskResponseDto adapterTaskResponseDto,
                                           Channel channel,
                                           @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {

        try {
            var testResultIdList = testTubeResultRepository.findTestResultByBarcode(adapterTaskResponseDto.getBarcode());
            measurementResultService.update(testResultIdList, adapterTaskResponseDto.getAdapterTaskResultList());
            channel.basicAck(tag, false);
        } catch (Exception ex) {
            channel.basicNack(tag, false, false);
        }
    }

    @RabbitListener(queues = RabbitMqConfig.ADAPTER_ERROR_QUEUE, ackMode = "MANUAL")
    public void processAdapterToLisErrorMessage(AdapterErrorDto adapterErrorDto,
                                                Channel channel,
                                                @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {

        try {
            testTubeResultService.updateErrorInfo(adapterErrorDto.getBarcode(), adapterErrorDto.getErrorId());
            channel.basicAck(tag, false);
        } catch (Exception ex) {
            channel.basicNack(tag, false, false);
        }
    }
}
