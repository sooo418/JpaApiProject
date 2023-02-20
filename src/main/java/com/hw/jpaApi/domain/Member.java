package com.hw.jpaApi.domain;

import com.hw.jpaApi.constants.AccountType;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert
@Entity
@Cacheable
@Table(uniqueConstraints = {@UniqueConstraint(name = "MEMBER_ACCOUNT_UNIQUE", columnNames = {"ACCOUNT_TYPE", "ACCOUNT_ID"})})
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NICKNAME", nullable = false, length = 30)
    private String nickname;

    @Column(name = "ACCOUNT_TYPE", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "ACCOUNT_ID", nullable = false, length = 10)
    private String accountId;

    @ColumnDefault("'N'")
    @Column(name = "QUIT", length = 1)
    private String quitYn;

    public Member() {
    }

    public Member(String nickname, AccountType accountType, String accountId) {
        this.nickname = nickname;
        this.accountType = accountType;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getQuitYn() {
        return quitYn;
    }

    public boolean hasId() {
        return this.id != null;
    }

    public boolean isQuit() {
        return "Y".equals(this.quitYn);
    }
}
