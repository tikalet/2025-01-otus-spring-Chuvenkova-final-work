package ru.otus.laboratory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.laboratory.repository.BarcodeRepository;

@RequiredArgsConstructor
@Service
public class BarcodeServiceImpl implements BarcodeService {

    private static final int MAX_SYMBOLS = 10;

    private final BarcodeRepository barcodeRepository;

    @Override
    public String generateBarcode() {
        Long value = barcodeRepository.getValue();
        String valueStr = String.valueOf(value);
        return "0".repeat(MAX_SYMBOLS - valueStr.length()) + valueStr;
    }
}
