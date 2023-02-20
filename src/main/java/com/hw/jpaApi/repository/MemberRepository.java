package com.hw.jpaApi.repository;

import com.hw.jpaApi.constants.AccountType;
import com.hw.jpaApi.domain.Member;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Cacheable(cacheNames = "findMemberCache", unless = "#result == null")
    Optional<Member> findByAccountTypeAndAccountId(AccountType accountType, String accountId);
}
