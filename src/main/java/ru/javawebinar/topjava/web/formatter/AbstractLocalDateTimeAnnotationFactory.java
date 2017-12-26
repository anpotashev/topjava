package ru.javawebinar.topjava.web.formatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public abstract class AbstractLocalDateTimeAnnotationFactory<A extends Annotation, S, K extends Formatter<S>> implements AnnotationFormatterFactory<A> {

    protected abstract Formatter<S> getFormatter();

    protected abstract Class<S> getClazz();

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(new Class<?>[] {getClazz()}));
    }

    @Override
    public Printer<S> getPrinter(A annotation, Class<?> aClass) {
        return getFormatter();
    }

    @Override
    public Parser<S> getParser(A annotation, Class<?> aClass) {
        return getFormatter();
    }

    @Component("dateAnnotationFactory")
    static class LocalDateAnnotationFactory extends AbstractLocalDateTimeAnnotationFactory<ALocalDate, LocalDate, LocalDateFormatter> {

        @Autowired
        private LocalDateFormatter formatter;


        @Override
        protected Formatter<LocalDate> getFormatter() {
            return formatter;
        }

        @Override
        protected Class<LocalDate> getClazz() {
            return LocalDate.class;
        }
    }

    @Component("timeAnnotationFactory")
    static class LocalTimeAnnotationFactory extends AbstractLocalDateTimeAnnotationFactory<ALocalTime, LocalTime, LocalTimeFormatter> {

        @Autowired
        private LocalTimeFormatter formatter;


        @Override
        protected Formatter<LocalTime> getFormatter() {
            return formatter;
        }

        @Override
        protected Class<LocalTime> getClazz() {
            return LocalTime.class;
        }
    }

    static abstract class AbstractLocalDateTimeFormatter<T extends TemporalAccessor> implements Formatter<T> {

        protected abstract DateTimeFormatter getFormatter();

        protected abstract T parse(String s);

        @Override
        public T parse(String s, Locale locale) throws ParseException {
            return s.length() == 0 ? null : parse(s);
        }

        @Override
        public String print(T t, Locale locale) {
            return t==null? "" : getFormatter().format(t);
        }
    }

    @Component
    public static class LocalTimeFormatter extends AbstractLocalDateTimeFormatter<LocalTime> {

        protected DateTimeFormatter formatter;

        public LocalTimeFormatter(@Value("HH:mm") String pattern) {
            this.formatter = DateTimeFormatter.ofPattern(pattern);
        }

        @Override
        protected DateTimeFormatter getFormatter() {
            return formatter;
        }

        @Override
        protected LocalTime parse(String s) {
            return LocalTime.parse(s, formatter);
        }
    }

    @Component
    public static class LocalDateFormatter extends AbstractLocalDateTimeFormatter<LocalDate> {

        private DateTimeFormatter formatter;

        public LocalDateFormatter(@Value("dd.MM.yyyy") String pattern) {
            this.formatter = DateTimeFormatter.ofPattern(pattern);
        }

        @Override
        protected DateTimeFormatter getFormatter() {
            return formatter;
        }

        @Override
        protected LocalDate parse(String s) {
            return LocalDate.parse(s, getFormatter());
        }

    }
}
