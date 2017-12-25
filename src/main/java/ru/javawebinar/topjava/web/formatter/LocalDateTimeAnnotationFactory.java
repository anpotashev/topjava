package ru.javawebinar.topjava.web.formatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component("annotationFactory")
public class LocalDateTimeAnnotationFactory implements AnnotationFormatterFactory<LocalDateOrLocalTime> {

    @Autowired
    private LocalDateFormatter dateFormatter;

    @Autowired
    private LocalTimeFormatter timeFormatter;

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(new Class<?>[] {LocalDate.class, LocalTime.class}));
    }

    @Override
    public Printer<?> getPrinter(LocalDateOrLocalTime annotation, Class<?> aClass) {
        return annotation.type() == LocalDateOrLocalTime.Type.DATE ? dateFormatter : timeFormatter;
    }

    @Override
    public Parser<?> getParser(LocalDateOrLocalTime annotation, Class<?> aClass) {
        return annotation.type() == LocalDateOrLocalTime.Type.DATE ? dateFormatter : timeFormatter;
    }

}
