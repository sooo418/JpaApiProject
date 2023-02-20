package com.hw.jpaApi.exception;

public class DeletedBoardException extends RuntimeException {
    public DeletedBoardException() {
    }

    public DeletedBoardException(String s) {
        super(s);
    }

    public DeletedBoardException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeletedBoardException(Throwable cause) {
        super(cause);
    }

    public DeletedBoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
