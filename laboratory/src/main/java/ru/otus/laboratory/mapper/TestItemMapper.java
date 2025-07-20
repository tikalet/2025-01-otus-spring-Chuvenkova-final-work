package ru.otus.laboratory.mapper;

import org.springframework.stereotype.Component;
import ru.otus.laboratory.dto.TestItemDto;
import ru.otus.laboratory.model.TestItem;

@Component
public class TestItemMapper {

    public TestItemDto fromModel(TestItem testItem) {
        TestItemDto testItemDto = new TestItemDto();
        testItemDto.setId(testItem.getId());
        testItemDto.setName(testItem.getName());
        testItemDto.setPrice(testItem.getPrice());
        return testItemDto;
    }

    public TestItem toModel(TestItemDto testItemDto) {
        TestItem testItem = new TestItem();
        testItem.setId(testItemDto.getId());
        testItem.setName(testItemDto.getName());
        testItem.setPrice(testItemDto.getPrice());
        return testItem;
    }
}
