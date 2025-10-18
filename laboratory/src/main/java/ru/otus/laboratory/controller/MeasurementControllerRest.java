package ru.otus.laboratory.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.laboratory.dto.MeasurementResultDto;
import ru.otus.laboratory.service.MeasurementResultService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MeasurementControllerRest {

    private final MeasurementResultService measurementResultService;

    @GetMapping("/api/measurementResult/testTubeResult/{barcode}")
    public ResponseEntity<List<MeasurementResultDto>> getMeasurementResultByTestTubeResultBarcode(
            @PathVariable("barcode") String barcode) {
        return new ResponseEntity<>(measurementResultService.findByTestTubeResultBarcode(barcode), HttpStatus.OK);
    }

    @GetMapping("/api/measurementResult/order/{id}")
    public ResponseEntity<List<MeasurementResultDto>> getMeasurementResultByOrderId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(measurementResultService.findByOrderId(id), HttpStatus.OK);
    }

    @GetMapping("/api/measurementResult/patient/{patientId}/measurementItemId/{measurementItemId}")
    public ResponseEntity<List<MeasurementResultDto>> getMeasurementResultByPatientAndItem(
            @NotNull @PathVariable("patientId") Long patientId,
            @NotNull @PathVariable("measurementItemId") Long measurementItemId) {
        return new ResponseEntity<>(measurementResultService
                .findByPatientIdAndMeasurementItemId(patientId, measurementItemId),
                HttpStatus.OK);
    }
}
