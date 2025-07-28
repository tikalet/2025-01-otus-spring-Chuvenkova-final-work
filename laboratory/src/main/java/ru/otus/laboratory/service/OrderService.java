package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.OrderResultCreateDto;
import ru.otus.laboratory.dto.OrderResultDto;
import ru.otus.laboratory.dto.OrderResultNurseDto;

import java.util.List;

public interface OrderService {

    OrderResultDto create(OrderResultCreateDto orderResultCreateDto);

    List<OrderResultNurseDto> findOrderForNurse();

    OrderResultNurseDto findOrderForNurseById(Long id);

    void updateOrderForNurse(Long id);

    List<OrderResultDto> findOrderByPatientId(Long patientId);

    void signTestTubeDocument(String barcode);
}
