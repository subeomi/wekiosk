package com.project.wekiosk.faq.search;

import com.project.wekiosk.faq.FaqDTO;
import com.project.wekiosk.faq.PageRequestDTO;
import com.project.wekiosk.faq.PageResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface FaqSearch {

    PageResponseDTO<FaqDTO> search(PageRequestDTO requestDTO);

    default Pageable makePageable(PageRequestDTO requestDTO){

        Pageable pageable =  PageRequest.of(
                requestDTO.getPage() -1,
                requestDTO.getSize(),
                Sort.by("qno").descending());

        return pageable;
    }
}
