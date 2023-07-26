package com.project.wekiosk.faq.service;

import com.project.wekiosk.domain.Faq;
import com.project.wekiosk.faq.dto.FaqDTO;
import com.project.wekiosk.faq.dto.PageRequestDTO;
import com.project.wekiosk.faq.dto.PageResponseDTO;
import jakarta.transaction.Transactional;

import java.util.Map;

@Transactional
public interface FaqService {

    PageResponseDTO<FaqDTO> list(PageRequestDTO pageRequestDTO);

    FaqDTO getOne(Long qno);

    Long register(FaqDTO dto);

    void modifier(FaqDTO dto);

    void delete(Long qno);
}
