package com.project.wekiosk.store.controller;

import com.project.wekiosk.member.MemberDTO;
import com.project.wekiosk.store.dto.StoreDTO;
import com.project.wekiosk.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/store/")
@RequiredArgsConstructor
@Log4j2
public class StoreController {

    private final StoreService storeService;


    @PostMapping("regist")
    public Map<String, String> regist(@RequestBody StoreDTO storeDTO){

        log.info(storeDTO);

        String sname = storeService.register(storeDTO);

        return Map.of("result", sname);
    }

    @GetMapping("{sno}")
    public StoreDTO getOne(@PathVariable("sno") Long sno){

        return storeService.getOne(sno);
    }

    @PutMapping("modify")
    public Map<String, String> modify(@RequestBody StoreDTO storeDTO){

        storeService.modifier(storeDTO);

        return Map.of("result", "변경완료");
    }

    @DeleteMapping("{sno}")
    public Map<String, String> delete(@PathVariable("sno") Long sno){

        storeService.delete(sno);

        return Map.of("result", "삭제완료");
    }
}
