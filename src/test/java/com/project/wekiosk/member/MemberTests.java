package com.project.wekiosk.member;

import com.project.wekiosk.member.domain.Member;
import com.project.wekiosk.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberRepository repository;

    @Test
    public void testInsert(){

        Member member = Member.builder()
                .memail("admin@kiosk.com")
                .mpw("1111")
                .mname("밥샵")
                .mgrade(0)
                .build();

        repository.save(member);
    }

}
