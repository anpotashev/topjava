package ru.javawebinar.topjava.web.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class LocalDateFormatter implements Formatter<LocalDate> {

    @Autowired
    private DateTimeFormatter dateFormatter;

    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        return LocalDate.parse(s, dateFormatter);
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return date.format(dateFormatter);
    }
}
