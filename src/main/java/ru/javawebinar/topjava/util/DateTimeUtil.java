package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetween(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        boolean result = startDate == null || ld.compareTo(startDate) >= 0;
        result &= endDate == null || endDate.compareTo(ld) >= 0;
        return result;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static String toString(LocalDate ld) { return ld == null ? "" : ld.format(DATE_FORMATTER); }

    public static String toString(LocalTime lt) { return (lt == null || lt == LocalTime.MIN || lt == LocalTime.MAX) ? "" : lt.format(TIME_FORMATTER); }
}
