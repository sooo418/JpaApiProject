package com.hw.jpaApi.constants;

import com.hw.jpaApi.exception.AccountTypeNotFoundException;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum AccountType {
    Realtor("REALTOR"),
    Lessor("LESSOR"),
    Lessee("LESSEE");

    final String name;

    AccountType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AccountType valueOfName(String type) {
        Optional<AccountType> findType = Arrays.stream(AccountType.values()).filter(e -> e.name.equals(type.toUpperCase())).findAny();
        return findType.orElseThrow(AccountTypeNotFoundException::new);
    }

    public static boolean containsType(String type) {
        if ( StringUtils.isEmpty(type) ) {
            return false;
        }
        return Arrays.stream(AccountType.values()).filter(e -> e.name.equals(type.toUpperCase())).findAny().isPresent();
    }
}
