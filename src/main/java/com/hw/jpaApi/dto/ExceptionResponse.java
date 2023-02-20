package com.hw.jpaApi.dto;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    private final String code;

    private final HttpStatus httpStatus;
    private final String message;

    public ExceptionResponse(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
