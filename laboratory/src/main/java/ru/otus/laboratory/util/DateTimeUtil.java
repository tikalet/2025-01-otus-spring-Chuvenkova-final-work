package ru.otus.laboratory.util;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeUtil {

    private static final String ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private static final String DATE = "yyyy-MM-dd";

    public String now() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ISO);
        return dateTimeFormatter.format(zonedDateTime);
    }

    public String nowDate() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE);
        return dateTimeFormatter.format(zonedDateTime);
    }

    public String plusDay(String sourceTime, int days) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ISO);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(sourceTime, dateTimeFormatter);
        zonedDateTime = zonedDateTime.plusDays(days);
        return dateTimeFormatter.format(zonedDateTime);
    }
}
