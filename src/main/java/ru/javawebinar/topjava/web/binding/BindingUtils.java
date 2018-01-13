package ru.javawebinar.topjava.web.binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.annotation.PostConstruct;
import java.util.StringJoiner;

@Component
public class BindingUtils {

    private static MessageSource ms;

    @Autowired private MessageSource messageSource;

    @PostConstruct
    private void init() {
        ms = messageSource;
    }

    public static ResponseEntity<String> checkBinding(BindingResult result) {
        StringJoiner joiner = new StringJoiner("<br>");
        result.getFieldErrors().forEach(
                fe -> {
                    String msg = ms.getMessage(fe, LocaleContextHolder.getLocale());
                    if (!msg.startsWith(fe.getField())) {
                        msg = fe.getField() + ' ' + msg;
                    }
                    joiner.add(msg);
                });
        return new ResponseEntity<>(joiner.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
