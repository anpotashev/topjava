package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    public static <T extends Comparable> boolean isBetween(T value, T startValue, T endValue) {
        return (startValue == null || value.compareTo(startValue)>=0)
                &&
                (endValue == null || endValue.compareTo(value)>=0);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static String toString(LocalDate ld) { return ld == null ? "" : ld.format(DATE_FORMATTER); }

    public static String toString(LocalTime lt) { return (lt == null || lt == LocalTime.MIN || lt == LocalTime.MAX) ? "" : lt.format(TIME_FORMATTER); }

}
