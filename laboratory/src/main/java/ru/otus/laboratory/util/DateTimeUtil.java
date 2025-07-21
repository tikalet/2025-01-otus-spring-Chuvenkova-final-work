package ru.otus.laboratory.util;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeUtil {

    private static final String ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public String now() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ISO);
        return dateTimeFormatter.format(zonedDateTime);
    }
}
