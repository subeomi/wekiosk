package com.project.wekiosk.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/member/")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
public class MemberController {

    private final MemberService memberService;


    @PostMapping("login")
    public MemberDTO login(@RequestBody MemberDTO memberDTO){

        log.info("Parameter: " + memberDTO);

        MemberDTO result = memberService.login(
                memberDTO.getMemail(),
                memberDTO.getMpw()
        );

        log.info("Return: " + result);

        return result;
    }

    @PostMapping("regist")
    public Map<String, String> regist(@RequestBody MemberDTO memberDTO){

        log.info(memberDTO);

        String memail = memberService.register(memberDTO);

        return Map.of("result", memail);
    }

    @GetMapping("{memail}")
    public MemberDTO getOne(@PathVariable("memail") String memail){

        return memberService.getOne(memail);
    }

    @PutMapping("modify")
    public Map<String, String> pwModify(@RequestBody MemberDTO memberDTO){

        memberService.pwModifier(memberDTO);

        return Map.of("result", "변경완료");
    }

    @DeleteMapping("{memail}")
    public Map<String, String> delete(@PathVariable("memail") String memail){

        memberService.delete(memail);
        
        return Map.of("result", "삭제완료");
    }

}
