package com.hw.jpaApi.controller.exception;

import com.hw.jpaApi.constants.ExceptionCode;
import com.hw.jpaApi.dto.ExceptionResponse;
import com.hw.jpaApi.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class)
    protected ResponseEntity<?> BoardNotFoundException(BoardNotFoundException e) {
        return handleExceptionInternal(ExceptionCode.NOT_FOUND_BOARD);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    protected ResponseEntity<?> memberNotFoundException(MemberNotFoundException e) {
        return handleExceptionInternal(ExceptionCode.NOT_FOUND_MEMBER);
    }

    @ExceptionHandler(AccountTypeNotFoundException.class)
    protected ResponseEntity<?> accountTypeNotFoundException(AccountTypeNotFoundException e) {
        return handleExceptionInternal(ExceptionCode.NOT_FOUND_ACCOUNT);
    }

    @ExceptionHandler(NonPermissionException.class)
    protected ResponseEntity<?> nonPermissionException(NonPermissionException e) {
        return handleExceptionInternal(ExceptionCode.NO_PERMISSION);
    }

    @ExceptionHandler(NoRequiredException.class)
    protected ResponseEntity<?> noRequiredException(NoRequiredException e) {
        return handleExceptionInternal(ExceptionCode.NO_REQUIRED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> constraintViolationException(ConstraintViolationException e) {
        return handleExceptionInternal(ExceptionCode.CONSTRAINT_VIOLATION);
    }

    @ExceptionHandler(DeletedBoardException.class)
    protected ResponseEntity<?> deletedBoardException(DeletedBoardException e) {
        return handleExceptionInternal(ExceptionCode.DELETED_BOARD);
    }

    @ExceptionHandler(WithDrawnMemberException.class)
    protected ResponseEntity<?> withDrawnMemberException(WithDrawnMemberException e) {
        return handleExceptionInternal(ExceptionCode.WITHDRAWN_MEMBER);
    }

    private ResponseEntity<Object> handleExceptionInternal(ExceptionCode exceptionCode) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(createExceptionResponse(exceptionCode));
    }

    private ExceptionResponse createExceptionResponse(ExceptionCode exceptionCode) {
        return new ExceptionResponse(exceptionCode.name(), exceptionCode.getHttpStatus(), exceptionCode.getMessage());
    }
}
