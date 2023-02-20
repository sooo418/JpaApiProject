package com.hw.jpaApi.exception;

import java.util.NoSuchElementException;

public class BoardNotFoundException extends NoSuchElementException {
    public BoardNotFoundException() {
    }

    public BoardNotFoundException(String message) {
        super(message);
    }
}
