package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.dto.StaffDto;
import ru.otus.laboratory.exceptions.NotFoundException;
import ru.otus.laboratory.mapper.StaffMapper;
import ru.otus.laboratory.model.Staff;
import ru.otus.laboratory.repository.StaffRepository;

@RequiredArgsConstructor
@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    private final StaffMapper staffMapper;


    @Override
    public StaffDto findById(Long id) {
        return staffMapper.fromModel(getStaff(id));
    }

    private Staff getStaff(long id) {
        return staffRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Staff with id %d not found".formatted(id)));
    }
}
