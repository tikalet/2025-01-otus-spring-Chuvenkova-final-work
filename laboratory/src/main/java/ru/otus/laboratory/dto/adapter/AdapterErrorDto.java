package ru.otus.laboratory.dto.adapter;

import lombok.Data;

@Data
public class AdapterErrorDto {

    private String barcode;

    private int error;
}
