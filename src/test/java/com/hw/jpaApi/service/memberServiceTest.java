package com.hw.jpaApi.service;

import com.hw.jpaApi.constants.AccountType;
import com.hw.jpaApi.domain.Member;
import com.hw.jpaApi.exception.WithDrawnMemberException;
import com.hw.jpaApi.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class memberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository repository;

    @Test
    void 회원등록() {
        //given
        Member realtor = new Member("김씨", AccountType.Realtor, "47");
        Member lessor = new Member("박씨", AccountType.Lessor, "21");
        Member lessee = new Member("이씨", AccountType.Lessee, "562");

        //when
//        repository.saveAll( Arrays.asList(realtor, lessor, lessee) );
        Member findMember = repository.findByAccountTypeAndAccountId(realtor.getAccountType(), realtor.getAccountId()).get();

        //then
        assertThat(findMember.getNickname()).isEqualTo(realtor.getNickname());
    }

    @Test
    void 회원조회() {
        //given
        String auth = "Realtor 47";

        //when
//        Member member = memberService.getMember(auth);

        //then
//        assertThat(member.getAccountType().getName()).isEqualTo(auth.split(" ")[0].toUpperCase());
//        assertThat(member.getAccountId()).isEqualTo(auth.split(" ")[1]);
        org.junit.jupiter.api.Assertions.assertThrows(WithDrawnMemberException.class, () -> memberService.getMember(auth));
    }
}
