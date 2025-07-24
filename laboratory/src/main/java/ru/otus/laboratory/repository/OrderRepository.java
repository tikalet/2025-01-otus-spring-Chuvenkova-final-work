package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.otus.laboratory.model.OrderResult;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderRepository {

    List<OrderResult> findByTimeAndStatus(@Param("time") String time, @Param("statusId") int statusId);

    Optional<OrderResult> findById(Long id);

    void create(OrderResult orderResult);

    void updateStatus(@Param("id") long id, @Param("statusId") int statusId);

}
