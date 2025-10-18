package ru.otus.adapter.rabbit;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import ru.otus.adapter.config.RabbitMqConfig;
import ru.otus.adapter.dto.AdapterTaskDto;
import ru.otus.adapter.service.ResultGenerateService;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMqListener {

    private final ResultGenerateService resultGenerateService;

    @RabbitListener(queues = RabbitMqConfig.LIS_TO_ADAPTER_QUEUE, ackMode = "MANUAL")
    public void processAdapterToLisMessage(AdapterTaskDto adapterTaskDto,
                                           Channel channel,
                                           @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {

        log.info("RECEIVED %s".formatted(adapterTaskDto));

        try {
            resultGenerateService.generateResultAndSend(adapterTaskDto);
            channel.basicAck(tag, false);
        } catch (Exception ex) {
            channel.basicNack(tag, false, false);
        }
    }

}
