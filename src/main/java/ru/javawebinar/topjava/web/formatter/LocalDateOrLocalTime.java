package ru.javawebinar.topjava.web.formatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateOrLocalTime {

    Type type() default Type.DATE;

    enum Type {
        DATE,
        TIME
    }

}
