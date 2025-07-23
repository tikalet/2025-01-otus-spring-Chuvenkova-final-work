package ru.otus.laboratory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.laboratory.dto.TestTubeResultDto;
import ru.otus.laboratory.service.TestTubeService;

@RequiredArgsConstructor
@RestController
public class TestTubeControllerRest {

    private final TestTubeService testTubeService;

    @GetMapping("/api/testTubeResult/{id}")
    public ResponseEntity<TestTubeResultDto> getTestTubeResultById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(testTubeService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/api/labAss/testTubeResult/{barcode}")
    public ResponseEntity<Void> recordArrivalTestTubeAtLaboratory(@PathVariable("barcode") String barcode) {
        testTubeService.recordArrivalTestTubeAtLaboratory(barcode);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
