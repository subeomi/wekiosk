package com.project.wekiosk.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    // 런타임익셉션 -> 언체크드익셉션
    public static final class MemberLoginException extends RuntimeException {

        public MemberLoginException(String msg){
            super(msg);
        }
    }

    @Override
    public MemberDTO login(String memail, String mpw) {

        MemberDTO memberDTO = null;

        try{
            Optional<Member> result = memberRepository.findById(memail);

            Member member = result.orElseThrow();

            if( !member.getMpw().equals(mpw)){
                throw new MemberLoginException("Password Incorrect");
            } else if(member.getMstatus() == 1){
                throw new MemberLoginException("Account Deleted");
            }

            memberDTO = MemberDTO.builder()
                    .memail(member.getMemail())
                    .mpw("") // 받을 땐 받고, 뱉을 땐 빈 문자열로.
                    .mname(member.getMname())
                    .mgrade(member.getMgrade())
                    .build();

        }catch (Exception e){
            throw new MemberLoginException(e.getMessage());

        }

        return memberDTO;
    }

    @Override
    public String register(MemberDTO memberDTO) {

        Member member = modelMapper.map(memberDTO, Member.class);

        memberRepository.save(member);

        return memberDTO.getMemail();
    }

    @Override
    public MemberDTO getOne(String memail) {

        Optional<Member> result = memberRepository.findById(memail);

        Member member = result.orElseThrow();

        MemberDTO dto = modelMapper.map(member, MemberDTO.class);

        return dto;
    }

    @Override
    public void pwModifier(MemberDTO memberDTO) {

        Optional<Member> result = memberRepository.findById(memberDTO.getMemail());

        Member member = result.orElseThrow();

        member.changeMpw(memberDTO.getMpw());

        memberRepository.save(member);
    }

    @Override
    public void delete(String memail) {

        Optional<Member> result = memberRepository.findById(memail);

        Member member = result.orElseThrow();

        member.delAccount();

        memberRepository.save(member);
    }


}
