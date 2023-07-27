package com.project.wekiosk.member;

import com.project.wekiosk.member.MemberDTO;
import jakarta.transaction.Transactional;

@Transactional
public interface MemberService {

    String register(MemberDTO memberDTO);

    MemberDTO getOne(String memail);

    void pwModifier(MemberDTO memberDTO);

    void delete(String memail);

    MemberDTO login(String memail, String mpw);
}
