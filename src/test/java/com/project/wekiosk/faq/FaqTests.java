package com.project.wekiosk.faq;

import com.project.wekiosk.domain.Faq;
import com.project.wekiosk.domain.Member;
import com.project.wekiosk.faq.repository.FaqRepository;
import com.project.wekiosk.member.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class FaqTests {

    @Autowired
    private FaqRepository repository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testInsert(){

        for(int i = 0; i < 5; i++){
            Faq faq = Faq.builder()
                    .qtitle("자주 묻는 질문 테스트"+i+"번")
                    .qcontent(i+"번 내용")

                    .build();

            repository.save(faq);
        }
    }

    @Test
    public void testReadOne(){

        Optional<Faq> result = repository.findById(1L);

        Faq faq = result.orElseThrow();

        log.info(faq);
    }

}
