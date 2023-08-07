package com.project.wekiosk.member.service;

import com.project.wekiosk.member.dto.MemberDTO;
import jakarta.transaction.Transactional;

import java.util.Map;

@Transactional
public interface MemberService {

    String register(MemberDTO memberDTO);

    MemberDTO getOne(String memail);

    void pwModifier(MemberDTO memberDTO);

    void delete(String memail);

    MemberDTO login(String memail, String mpw, String fcmtoken);

    int duplicateCheck(String memail);

    Map<String, Object> sendSimpleMessage(String to)throws Exception;
}
