package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.StaffDto;
import ru.otus.laboratory.model.Staff;

@Component
public class StaffMapper {

    public StaffDto fromModel(Staff staff) {
        StaffDto staffDto = new StaffDto();
        staffDto.setId(staff.getId());
        staffDto.setLastName(staff.getLastName());
        staffDto.setFirstName(staff.getFirstName());
        staffDto.setMiddleName(staff.getMiddleName());
        return staffDto;
    }

    public Staff toModel(StaffDto staffDto) {
        Staff staff = new Staff();
        staff.setId(staffDto.getId());
        staff.setLastName(staffDto.getLastName());
        staff.setFirstName(staffDto.getFirstName());
        staff.setMiddleName(staffDto.getMiddleName());
        return staff;
    }
}
