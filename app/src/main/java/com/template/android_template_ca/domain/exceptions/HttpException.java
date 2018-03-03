package com.template.android_template_ca.domain.exceptions;

public class HttpException extends RuntimeException {

    private int errorCode;
    private String message;
    private Throwable throwable;

    public HttpException(Throwable throwable) {
        super(throwable);
        errorCode = ((retrofit2.HttpException)throwable).code();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

}
