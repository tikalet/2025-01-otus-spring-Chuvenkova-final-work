package ru.otus.laboratory.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.otus.laboratory.model.Staff;
import ru.otus.laboratory.model.StaffAuth;

import java.util.Optional;

@Mapper
public interface StaffRepository {

    Optional<Staff> findById(Long id);

    Optional<StaffAuth> findByLogin(String login);
}
