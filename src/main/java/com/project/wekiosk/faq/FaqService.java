package com.project.wekiosk.faq;

import com.project.wekiosk.faq.FaqDTO;
import com.project.wekiosk.faq.PageRequestDTO;
import com.project.wekiosk.faq.PageResponseDTO;
import jakarta.transaction.Transactional;

@Transactional
public interface FaqService {

    PageResponseDTO<FaqDTO> list(PageRequestDTO pageRequestDTO);

    FaqDTO getOne(Long qno);

    Long register(FaqDTO dto);

    void modifier(FaqDTO dto);

    void delete(Long qno);
}
