package ru.otus.laboratory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.service.TestItemService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestItemControllerRest {

    private final TestItemService testItemService;

    @GetMapping("/api/testItem")
    public ResponseEntity<List<TestItemDto>> getTestItem() {
        return new ResponseEntity<>(testItemService.findAll(), HttpStatus.OK);
    }

  
}
