package ru.javawebinar.topjava.web.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class LocalTimeFormatter implements Formatter<LocalTime> {

    @Autowired
    private DateTimeFormatter timeFormatter;

    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        return LocalTime.parse(s, timeFormatter);
    }

    @Override
    public String print(LocalTime time, Locale locale) {
        return time.format(timeFormatter);
    }
}
