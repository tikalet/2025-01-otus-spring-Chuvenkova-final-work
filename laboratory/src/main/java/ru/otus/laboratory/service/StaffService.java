package ru.otus.laboratory.service;

import ru.otus.laboratory.dto.StaffDto;

public interface StaffService {

    StaffDto findById(Long id);
}
