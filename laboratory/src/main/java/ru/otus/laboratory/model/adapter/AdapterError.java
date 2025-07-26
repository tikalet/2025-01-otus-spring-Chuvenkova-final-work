package ru.otus.laboratory.model.adapter;

import lombok.Data;

@Data
public class AdapterError {

    private String barcode;

    private int error;
}
