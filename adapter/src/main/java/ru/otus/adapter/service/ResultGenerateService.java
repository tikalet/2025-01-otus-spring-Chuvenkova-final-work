package ru.otus.adapter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.adapter.dto.AdapterErrorDto;
import ru.otus.adapter.dto.AdapterTaskDto;
import ru.otus.adapter.dto.AdapterTaskResponseDto;
import ru.otus.adapter.dto.AdapterTaskResultDto;
import ru.otus.adapter.rabbit.RabbitMqSender;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ResultGenerateService {

    private static final int[] ERROR_CODE = {1, 11, 21, 31, 41};

    private final RabbitMqSender rabbitMqSender;

    public void generateResultAndSend(AdapterTaskDto adapterTaskDto) {
        AdapterTaskResponseDto adapterTaskResponseDto = new AdapterTaskResponseDto();
        adapterTaskResponseDto.setBarcode(adapterTaskDto.getBarcode());
        adapterTaskResponseDto.setAdapterTaskResultList(new ArrayList<>());

        for (String extcode : adapterTaskDto.getExtcodes()) {
            AdapterTaskResultDto adapterTaskResultDto = new AdapterTaskResultDto();
            adapterTaskResultDto.setExtcode(extcode);
            adapterTaskResultDto.setValue(generateValue());
            adapterTaskResponseDto.getAdapterTaskResultList().add(adapterTaskResultDto);
        }

        delay();

        if (hasProblem()) {
            AdapterErrorDto adapterErrorDto = new AdapterErrorDto();
            adapterErrorDto.setBarcode(adapterTaskDto.getBarcode());
            adapterErrorDto.setErrorId(generateErrorCode());
            rabbitMqSender.sendToLisError(adapterErrorDto);
        } else {
            rabbitMqSender.sendToLisResultData(adapterTaskResponseDto);
        }
    }

    private Double generateValue() {
        double value = new Random().nextDouble(1, 30);
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void delay() {
        long ms = new Random().nextInt(1000, 5000);

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasProblem() {
        return (new Random().nextInt(0, 1000)) < 200;
    }

    private int generateErrorCode() {
        return ERROR_CODE[new Random().nextInt(0, ERROR_CODE.length)];
    }
}
