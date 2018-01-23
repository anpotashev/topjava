package ru.javawebinar.topjava.util.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.PreparedStatementCallback;

@Aspect
//@Component
public class AspectUtil {

    @Pointcut("@annotation(ru.javawebinar.topjava.util.aop.CheckForDataIntegrityViolationException)")
    private void checkForDataIntegrityViolationException() {
    }

    @AfterThrowing(value = "checkForDataIntegrityViolationException() && @annotation(annotation) && args(data,..)", throwing = "exception")
    public void checkResult1(CheckForDataIntegrityViolationException annotation, Throwable exception, Object data) {
        if (exception instanceof DataIntegrityViolationException
                || exception instanceof PreparedStatementCallback //for jdbc
                ) {
            throw new DuplicateFieldException(exception, data, annotation.creating(), annotation.msgCode());
        }
    }

}
