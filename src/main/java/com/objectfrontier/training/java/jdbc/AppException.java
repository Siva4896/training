package com.objectfrontier.training.java.jdbc;

public class AppException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public ExceptionCode errorMessage;

    public AppException(ExceptionCode error) {
        errorMessage = error;
    }

    public String getErrorMessage() {
        return errorMessage.getErrorMessage();
    }
}
