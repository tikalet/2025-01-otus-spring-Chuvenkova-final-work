package ru.otus.laboratory.dto.adapter;

import lombok.Data;

import java.util.List;

@Data
public class AdapterTaskResponseDto {

    private String barcode;

    private List<AdapterTaskResultDto> adapterTaskResultList;
}
