package com.hw.jpaApi.service;

import com.hw.jpaApi.constants.AccountType;
import com.hw.jpaApi.domain.Member;
import com.hw.jpaApi.exception.ConstraintViolationException;
import com.hw.jpaApi.exception.MemberNotFoundException;
import com.hw.jpaApi.exception.WithDrawnMemberException;
import com.hw.jpaApi.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;

@Service
public class MemberService {

    Logger logger = LoggerFactory.getLogger(MemberService.class);

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMember(String auth) {

        if ( StringUtils.isEmpty(auth) ) {
            return new Member();
        }

        try {
            String[] authInf = auth.split(" ");

            AccountType accountType = AccountType.valueOfName(authInf[0]);
            String accountId = authInf[1];

            Optional<Member> findMemeberOpt = memberRepository.findByAccountTypeAndAccountId(accountType, accountId);

            Member findMemeber = findMemeberOpt.orElseThrow(MemberNotFoundException::new);

            if (findMemeber.isQuit()) {
                throw new WithDrawnMemberException();
            }
            return findMemeber;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConstraintViolationException();
        }
    }
}
