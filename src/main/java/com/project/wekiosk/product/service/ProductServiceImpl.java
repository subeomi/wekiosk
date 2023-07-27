package com.project.wekiosk.product.service;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.category.repository.CategoryRepository;
import com.project.wekiosk.page.dto.PageRequestDTO;
import com.project.wekiosk.page.dto.PageResponseDTO;
import com.project.wekiosk.product.domain.Product1;
import com.project.wekiosk.product.domain.ProductImage;
import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.dto.ProductListDTO;
import com.project.wekiosk.product.repository.ProductRepository;
import com.project.wekiosk.util.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService{

    private final FileUploader fileUploader;
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Long register(ProductDTO productDTO) {

        Category category = categoryRepository.findByCateno(productDTO.getCateno());
        Product1 product = Product1.builder()
                .pname(productDTO.getPname())
                .pprice(productDTO.getPprice())
                .build();

        productDTO.getImages().forEach(fname -> {
            product.addImage(fname);
        });

        product.setCategory(category);

        return productRepository.save(product).getPno();
    }

    @Override
    public ProductDTO readOne(Long pno) {

        Product1 product = productRepository.selectOne(pno);

        ProductDTO dto = ProductDTO.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .pprice(product.getPprice())
                .images(product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList()))
                .build();
        return dto;
    }

    @Override
    public void remove(Long pno) {
        Product1 product = productRepository.findById(pno)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. pno: " + pno));

        List<ProductImage> images = product.getImages();
        List<String> fileNames = images.stream()
                .map(ProductImage::getFname)
                .collect(Collectors.toList());

        // 파일 삭제
        fileUploader.removeFiles(fileNames);

        // 상품 삭제
        productRepository.delete(product);
    }




    @Override
    public void modify(ProductDTO productDTO) {


        Optional<Product1> result = productRepository.findById(productDTO.getPno());

        Product1 product = result.orElseThrow();

        product.changePname(productDTO.getPname());
        product.changePprice(productDTO.getPprice());

        List<String> oldFileNames = product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

        product.clearImage();

        productDTO.getImages().forEach(fname -> product.addImage(fname));

        log.info(product);

        productRepository.save(product);

        List<String> newFiles = productDTO.getImages();
        List<String> wantDeleteFiles = oldFileNames.stream()
                .filter(f -> newFiles.indexOf(f) == -1)
                .collect(Collectors.toList());

        log.info(wantDeleteFiles);

        fileUploader.removeFiles(wantDeleteFiles);

    }
}
