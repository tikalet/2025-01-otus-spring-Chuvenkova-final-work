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

        return stringBuilder.toString();
    }
}
