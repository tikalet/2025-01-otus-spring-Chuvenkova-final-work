package ru.otus.laboratory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.laboratory.dto.OrderResultCreateDto;
import ru.otus.laboratory.dto.OrderResultDto;
import ru.otus.laboratory.service.OrderService;

@RequiredArgsConstructor
@RestController
public class OrderControllerRest {

    private final OrderService orderService;

    @PostMapping("/api/order")
    public ResponseEntity<OrderResultDto> createPatient(@Valid @RequestBody OrderResultCreateDto orderResultCreateDto) {
        var orderResultDto = orderService.create(orderResultCreateDto);
        return new ResponseEntity<OrderResultDto>(orderResultDto, HttpStatus.CREATED);
    }
}
