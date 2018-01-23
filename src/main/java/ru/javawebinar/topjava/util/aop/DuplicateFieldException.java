package ru.javawebinar.topjava.util.aop;

public class DuplicateFieldException extends RuntimeException {

    private Object wrongData;
    private boolean creating;
    private String msgCode;

    public Object getWrongData() {
        return wrongData;
    }

    public boolean isCreating() {
        return creating;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public DuplicateFieldException(Throwable cause, Object wrongData, boolean creating, String msgCode) {
        super(cause);
        this.wrongData = wrongData;
        this.creating = creating;
        this.msgCode = msgCode;
    }
}
