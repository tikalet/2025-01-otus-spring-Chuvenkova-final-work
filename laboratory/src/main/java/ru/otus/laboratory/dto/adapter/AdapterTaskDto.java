package ru.otus.laboratory.dto.adapter;

import lombok.Data;

import java.util.List;

@Data
public class AdapterTaskDto {

    private String barcode;

    private List<String> extcodes;
}
