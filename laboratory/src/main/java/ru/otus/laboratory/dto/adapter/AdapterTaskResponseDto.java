package ru.otus.laboratory.dto.adapter;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AdapterTaskResponseDto {

    @NotBlank
    private String barcode;

    private List<AdapterTaskResultDto> adapterTaskResultList;
}
