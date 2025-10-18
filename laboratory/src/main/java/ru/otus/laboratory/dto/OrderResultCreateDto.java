package ru.otus.laboratory.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResultCreateDto {

    @NotNull
    private Long patientId;

    @NotNull
    private Long staffId;

    @NotEmpty
    private List<@NotNull @Positive Long> testItemIdList;
}
