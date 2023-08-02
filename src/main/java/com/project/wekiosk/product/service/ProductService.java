package com.project.wekiosk.product.service;

import com.project.wekiosk.page.dto.PageRequestDTO;
import com.project.wekiosk.page.dto.PageResponseDTO;
import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.dto.ProductListDTO;

public interface ProductService {

    PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO);

    Long register(ProductDTO productDTO);

    ProductDTO readOne(Long pno);

    void remove(Long pno);

    void modify(ProductDTO productDTO);
}
