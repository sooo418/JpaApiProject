package com.hw.jpaApi.constants;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "해당 게시글은 존재하지 않습니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, "해당 계정 유형은 존재하지 않습니다."),
    NO_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NO_REQUIRED(HttpStatus.BAD_REQUEST, "필수값이 누락되었습니다."),
    CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, "다시 시도해주세요"),
    DELETED_BOARD(HttpStatus.NOT_FOUND, "삭제된 게시글입니다."),
    WITHDRAWN_MEMBER(HttpStatus.NOT_FOUND, "탈퇴한 회원입니다.");


    private final HttpStatus httpStatus;
    private final String message;

    ExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
