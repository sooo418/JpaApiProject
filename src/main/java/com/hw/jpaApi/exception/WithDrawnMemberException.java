package com.hw.jpaApi.exception;

public class WithDrawnMemberException extends RuntimeException {
    public WithDrawnMemberException() {
    }

    public WithDrawnMemberException(String message) {
        super(message);
    }

    public WithDrawnMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public WithDrawnMemberException(Throwable cause) {
        super(cause);
    }

    public WithDrawnMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
