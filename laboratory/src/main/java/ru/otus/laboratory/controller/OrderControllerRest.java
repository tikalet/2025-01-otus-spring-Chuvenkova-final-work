package ru.otus.laboratory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.laboratory.dto.OrderResultCreateDto;
import ru.otus.laboratory.dto.OrderResultDto;
import ru.otus.laboratory.dto.OrderResultNurseDto;
import ru.otus.laboratory.service.OrderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderControllerRest {

    private final OrderService orderService;

    @GetMapping("/api/order/patient/{patientId}")
    public ResponseEntity<List<OrderResultDto>> getOrderByPatientId(@PathVariable("patientId") Long patientId) {
        return new ResponseEntity<>(orderService.findOrderByPatientId(patientId), HttpStatus.OK);
    }

    @PostMapping("/api/order")
    public ResponseEntity<OrderResultDto> createOrder(@Valid @RequestBody OrderResultCreateDto orderResultCreateDto) {
        var orderResultDto = orderService.create(orderResultCreateDto);
        return new ResponseEntity<OrderResultDto>(orderResultDto, HttpStatus.CREATED);
    }

    @GetMapping("/api/nurse/order")
    public ResponseEntity<List<OrderResultNurseDto>> getActualOrderForNurse() {
        return new ResponseEntity<>(orderService.findOrderForNurse(), HttpStatus.OK);
    }

    @GetMapping("/api/nurse/order/{id}")
    public ResponseEntity<OrderResultNurseDto> getOrderForNurse(@PathVariable("id") Long id) {
        return new ResponseEntity<>(orderService.findOrderForNurseById(id), HttpStatus.OK);
    }

    @PutMapping("/api/nurse/order/{id}")
    public ResponseEntity<Void> updateOrderForNurse(@PathVariable("id") Long id) {
        orderService.updateOrderForNurse(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/api/doctor/order/barcode/{barcode}")
    public ResponseEntity<Void> signOrderDocument(@PathVariable("barcode") String barcode) {
        orderService.signTestTubeDocument(barcode);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
