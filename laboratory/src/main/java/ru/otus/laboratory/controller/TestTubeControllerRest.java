package ru.otus.laboratory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.laboratory.dto.TestTubeItemDto;
import ru.otus.laboratory.service.TestTubeService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestTubeControllerRest {

    private final TestTubeService testTubeService;

    @GetMapping("/api/testTubeItem")
    public ResponseEntity<List<TestTubeItemDto>> getTestItem() {
        return new ResponseEntity<>(testTubeService.findAll(), HttpStatus.OK);
    }
}
