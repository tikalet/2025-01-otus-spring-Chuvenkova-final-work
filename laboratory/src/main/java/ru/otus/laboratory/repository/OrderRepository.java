package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.OrderResult;

import java.util.List;

@Mapper
public interface OrderRepository {

    List<OrderResult> findByTime(String time);

    List<OrderResult> findById(Long id);

    void create(OrderResult orderResult);
    
}
