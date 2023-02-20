package com.hw.jpaApi.exception;

public class NoRequiredException extends RuntimeException {
    public NoRequiredException() {
    }

    public NoRequiredException(String message) {
        super(message);
    }

    public NoRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRequiredException(Throwable cause) {
        super(cause);
    }

    public NoRequiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
