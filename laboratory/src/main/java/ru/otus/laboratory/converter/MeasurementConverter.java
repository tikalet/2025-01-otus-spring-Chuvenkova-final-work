package ru.otus.laboratory.converter;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.model.search.MeasurementResultSearch;

@Component
public class MeasurementConverter {

    public String measurementResultSearchToString(MeasurementResultSearch measurementResultSearch) {
        StringBuilder stringBuilder = new StringBuilder();

        if (measurementResultSearch.getOrderId() != null) {
            stringBuilder.append(" orderId= ").append(measurementResultSearch.getOrderId());
        }

        if (measurementResultSearch.getBarcode() != null && !measurementResultSearch.getBarcode().isEmpty()) {
            stringBuilder.append(" barcode= ").append(measurementResultSearch.getBarcode());
        }

        if (measurementResultSearch.getMeasurementItemId() != null) {
            stringBuilder.append(" measurementItemId= ").append(measurementResultSearch.getMeasurementItemId());
        }

        if (measurementResultSearch.getPatientId() != null) {
            stringBuilder.append(" patientId== ").append(measurementResultSearch.getPatientId());
        }

        return stringBuilder.toString();
    }
}
