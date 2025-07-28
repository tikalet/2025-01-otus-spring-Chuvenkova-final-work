package ru.otus.laboratory.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.laboratory.dto.TestTubeErrorDto;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.dto.TestTubeResultErrorUpdateDto;
import ru.otus.laboratory.rabbit.RabbitMqSender;
import ru.otus.laboratory.service.TestTubeErrorService;
import ru.otus.laboratory.service.TestTubeResultService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestTubeControllerRest {

    private final TestTubeResultService testTubeResultService;

    private final TestTubeErrorService testTubeErrorService;

    private final RabbitMqSender rabbitMqSender;

    @GetMapping("/api/testTubeError")
    public ResponseEntity<List<TestTubeErrorDto>> getTestTubeError() {
        return new ResponseEntity<>(testTubeErrorService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/testTubeResult/id/{id}")
    public ResponseEntity<TestTubeResultDto> getTestTubeResultById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(testTubeResultService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/api/testTubeResult/barcode/{barcode}")
    public ResponseEntity<TestTubeResultDto> getTestTubeResultByBarcode(@PathVariable("barcode") String barcode) {
        return new ResponseEntity<>(testTubeResultService.findByBarcode(barcode), HttpStatus.OK);
    }

    @GetMapping("/api/testTubeResult/time/{time}/status/{status}")
    public ResponseEntity<List<TestTubeResultDto>> getTestTubeResultByTimeAndStatus(
            @Valid @PathVariable("time")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Time parameter must be in format yyyy-MM-dd")
            String time,
            @PathVariable("status") Long statusId) {
        return new ResponseEntity<>(testTubeResultService.findByTimeAndStatus(time, statusId),
                HttpStatus.OK);
    }

    @GetMapping("/api/testTubeResult/status/{status}")
    public ResponseEntity<List<TestTubeResultDto>> getTestTubeResultByStatus(
            @PathVariable("status") Long statusId) {
        return new ResponseEntity<>(testTubeResultService.findByTimeAndStatus(null, statusId), HttpStatus.OK);
    }

    @PutMapping("/api/testTubeResult/error")
    public ResponseEntity<TestTubeResultDto> updateErrorInfo(
            @Valid @RequestBody TestTubeResultErrorUpdateDto testTubeResultErrorUpdateDto) {
        return new ResponseEntity<>(testTubeResultService.updateErrorInfo(testTubeResultErrorUpdateDto), HttpStatus.OK);
    }

    @PutMapping("/api/labAss/testTubeResult/{barcode}")
    public ResponseEntity<Void> recordArrivalTestTubeAtLaboratory(@PathVariable("barcode") String barcode) {
        testTubeResultService.recordArrivalTestTubeAtLaboratory(barcode);

        try {
            rabbitMqSender.sendToAdapterMeasurementByBarcode(barcode);
        } catch (AmqpException ex) {
            log.error("Unable send measurement extcodes by barcode %s to Rabbit".formatted(barcode), ex);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
