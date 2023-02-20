package com.hw.jpaApi.annotation.aspect;

import com.hw.jpaApi.constants.AccountType;
import com.hw.jpaApi.exception.NonPermissionException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthCheckAspect {

    @Pointcut("@annotation(com.hw.jpaApi.annotation.AuthCheck)")
    public void authCheck() {

    }

    @Before("authCheck()")
    public void AuthenticationCheck() {
        // 어플리케이션에서 Reqeust 객체를 불러옴
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String auth = "";

        if ( request != null ) {
            auth = request.getHeader("authentication");
        }

        if ( !AccountType.containsType( auth.split(" ")[0] ) ) {
            throw new NonPermissionException();
        }

    }
}
