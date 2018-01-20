package ru.javawebinar.topjava.util;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.javawebinar.topjava.HasId;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.StringJoiner;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static ResponseEntity<String> getErrorResponse(BindingResult result) {
        StringJoiner joiner = new StringJoiner("<br>");
        result.getFieldErrors().forEach(
                fe -> {
                    String msg = fe.getDefaultMessage();
                    if (!msg.startsWith(fe.getField())) {
                        msg = fe.getField() + ' ' + msg;
                    }
                    joiner.add(msg);
                });
        return new ResponseEntity<>(joiner.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    public static Unique_idx parseDataIntegrityViolationExceptionAndReturnUniqueIdx(DataIntegrityViolationException ex) {
        String message = ValidationUtil.getRootCause(ex).getMessage();
        Unique_idx idx = Unique_idx.find(message);
        return idx;
    }

    public enum Unique_idx {
        USER_EMAIL("users_unique_email_idx")
        , MEAL_DATETIME("meals_unique_user_datetime_idx");
        String indexName;
        String msgCode;

        public String getMsgCode() {
            return msgCode;
        }
        Unique_idx(String indexName){
            this.indexName = indexName;
            msgCode = "duplicate."+indexName;
        }
        static Unique_idx find(String  str) {
            for (Unique_idx idx : Unique_idx.values()) {
                if (str.contains(idx.indexName)) {
                    return idx;
                }
            }
            return null;
        }
    }
}