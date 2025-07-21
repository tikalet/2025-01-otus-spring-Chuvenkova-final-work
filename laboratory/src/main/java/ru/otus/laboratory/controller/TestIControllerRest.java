package ru.otus.laboratory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.service.TestService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestIControllerRest {

    private final TestService testService;

    @GetMapping("/api/testItem")
    public ResponseEntity<List<TestItemDto>> getTestItem() {
        return new ResponseEntity<>(testService.findAll(), HttpStatus.OK);
    }


}
