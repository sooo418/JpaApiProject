package com.hw.jpaApi.exception;

import java.util.NoSuchElementException;

public class MemberNotFoundException extends NoSuchElementException {
    public MemberNotFoundException() {
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
