package com.project.wekiosk.product.service;

import com.project.wekiosk.page.dto.PageRequestDTO;
import com.project.wekiosk.page.dto.PageResponseDTO;
import com.project.wekiosk.product.domain.Product1;
import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.dto.ProductListDTO;
import com.project.wekiosk.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService{



    private final ProductRepository productRepository;

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Long register(ProductDTO productDTO) {
        Product1 product = Product1.builder()
                .pname(productDTO.getPname())
                .pprice(productDTO.getPprice())
                .build();

        return productRepository.save(product).getPno();
    }

    @Override
    public ProductDTO readOne(Long pno) {
        return null;
    }

    @Override
    public void remove(Long pno) {

    }

    @Override
    public void modify(ProductDTO productDTO) {

    }
}
