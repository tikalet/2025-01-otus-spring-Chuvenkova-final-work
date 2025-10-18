package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.otus.laboratory.model.OrderResult;
import ru.otus.laboratory.model.search.OrderSearch;

import java.util.List;

@Mapper
public interface OrderRepository {

    List<OrderResult> findByParam(@Param("search") OrderSearch orderSearch,
                                  @Param("searchCondition") String searchCondition);

    void create(OrderResult orderResult);

    void updateStatus(@Param("id") long id, @Param("statusId") int statusId);


}
