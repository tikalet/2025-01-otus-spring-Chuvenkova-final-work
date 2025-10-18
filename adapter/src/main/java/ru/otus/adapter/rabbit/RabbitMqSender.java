package ru.otus.adapter.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.adapter.dto.AdapterErrorDto;
import ru.otus.adapter.dto.AdapterTaskResponseDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMqSender {

    private static final String ADAPTER_TO_LIS_ROUTE_KEY = "adapter.to.lis.route.key";

    private static final String ADAPTER_ERROR_ROUTE_KEY = "adapter.error.route.key";

    private final RabbitTemplate rabbitTemplate;

    public void sendToLisResultData(AdapterTaskResponseDto adapterTaskResponseDto) {
        log.info("SENT %s".formatted(adapterTaskResponseDto));
        rabbitTemplate.convertAndSend(ADAPTER_TO_LIS_ROUTE_KEY, adapterTaskResponseDto);
    }

    public void sendToLisError(AdapterErrorDto adapterErrorDto) {
        log.info("SENT %s".formatted(adapterErrorDto));
        rabbitTemplate.convertAndSend(ADAPTER_ERROR_ROUTE_KEY, adapterErrorDto);
    }
}
