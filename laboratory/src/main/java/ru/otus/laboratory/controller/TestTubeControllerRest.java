package ru.otus.laboratory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import ru.otus.laboratory.service.TestTubeErrorService;
import ru.otus.laboratory.service.TestTubeResultService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestTubeControllerRest {

    private final TestTubeResultService testTubeResultService;

    private final TestTubeErrorService testTubeErrorService;

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

    @PutMapping("/api/testTubeResult/error")
    public ResponseEntity<TestTubeResultDto> updateErrorInfo(
            @Valid @RequestBody TestTubeResultErrorUpdateDto testTubeResultErrorUpdateDto) {
        return new ResponseEntity<>(testTubeResultService.updateErrorInfo(testTubeResultErrorUpdateDto), HttpStatus.OK);
    }

    @PutMapping("/api/labAss/testTubeResult/{barcode}")
    public ResponseEntity<Void> recordArrivalTestTubeAtLaboratory(@PathVariable("barcode") String barcode) {
        testTubeResultService.recordArrivalTestTubeAtLaboratory(barcode);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
