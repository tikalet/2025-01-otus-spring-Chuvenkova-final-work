package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BarcodeRepository {

    Long getValue();
}
