package ru.otus.laboratory.model.adapter;

import lombok.Data;

import java.util.List;

@Data
public class AdapterTaskResponse {

    private String barcode;

    private List<AdapterTaskResult> adapterTaskResultList;
}
