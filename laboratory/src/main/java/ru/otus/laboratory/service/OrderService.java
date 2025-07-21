package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.OrderResultCreateDto;
import ru.otus.laboratory.dto.OrderResultDto;

public interface OrderService {

    OrderResultDto create(OrderResultCreateDto orderResultCreateDto);
}
