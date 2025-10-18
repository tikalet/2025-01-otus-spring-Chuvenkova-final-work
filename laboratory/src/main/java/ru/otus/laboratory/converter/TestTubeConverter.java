package ru.otus.laboratory.converter;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.model.search.TestTubeResultSearch;

@Component
public class TestTubeConverter {

    public String testTubeResultSearchToString(TestTubeResultSearch testTubeResultSearch) {
        StringBuilder stringBuilder = new StringBuilder();

        if (testTubeResultSearch.getId() != null) {
            stringBuilder.append(" id= ").append(testTubeResultSearch.getId());
        }

        if (testTubeResultSearch.getOrderId() != null) {
            stringBuilder.append(" orderId= ").append(testTubeResultSearch.getOrderId());
        }

        if (testTubeResultSearch.getBarcode() != null && !testTubeResultSearch.getBarcode().isEmpty()) {
            stringBuilder.append(" barcode= ").append(testTubeResultSearch.getBarcode());
        }

        if (testTubeResultSearch.getTime() != null && !testTubeResultSearch.getTime().isEmpty()) {
            stringBuilder.append(" time= ").append(testTubeResultSearch.getTime());
        }

        if (testTubeResultSearch.getStatusId() != null) {
            stringBuilder.append(" statusId= ").append(testTubeResultSearch.getStatusId());
        }

        return stringBuilder.toString();
    }
}
