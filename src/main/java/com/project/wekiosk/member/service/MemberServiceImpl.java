package com.project.wekiosk.member.service;

import com.project.wekiosk.domain.Faq;
import com.project.wekiosk.domain.Member;
import com.project.wekiosk.faq.dto.FaqDTO;
import com.project.wekiosk.member.dto.MemberDTO;
import com.project.wekiosk.member.repository.MemberRepository;
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
