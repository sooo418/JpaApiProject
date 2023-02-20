package com.hw.jpaApi.exception;

import java.util.NoSuchElementException;

public class AccountTypeNotFoundException extends NoSuchElementException {
    public AccountTypeNotFoundException() {
    }

    public AccountTypeNotFoundException(String s) {
        super(s);
    }
}
