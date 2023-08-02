package com.project.wekiosk.product.service;

import com.project.wekiosk.page.dto.PageRequestDTO;
import com.project.wekiosk.page.dto.PageResponseDTO;
import com.project.wekiosk.product.domain.Product1;
import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.dto.ProductListDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product1> findProductInCategory(Long cateno, Long pno);

    PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO);

    Long register(ProductDTO productDTO);


    void remove(Long pno);

    void modifyProduct(Long pno, ProductDTO productDTO);

    List<Product1> getProductsByCategory(Long cateno);

    ProductDTO readOneInCategory(Long cateno, Long pno);

    Long setProduct(ProductDTO productDTO);

}
