package com.project.wekiosk.product.service;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.category.repository.CategoryRepository;
import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.option.dto.OptionsDTO;
import com.project.wekiosk.option.repository.OptionsRepository;
import com.project.wekiosk.page.dto.PageRequestDTO;
import com.project.wekiosk.page.dto.PageResponseDTO;
import com.project.wekiosk.product.domain.Product1;
import com.project.wekiosk.product.domain.ProductImage;
import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.dto.ProductListDTO;
import com.project.wekiosk.product.repository.ProductRepository;
import com.project.wekiosk.util.FileUploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final FileUploader fileUploader;
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final OptionsRepository optionsRepository;

    @Override
    public Optional<Product1> findProductInCategory(Long cateno, Long pno) {
        return productRepository.findProductInCategory(cateno, pno);
    }
    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {
        return null;

    }



    @Override
    @Transactional
    public Long register(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCateno())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. cateno=" + productDTO.getCateno()));

        Long savedPno = setProduct(productDTO); // setProduct() 메서드를 호출하여 상품과 옵션들을 데이터베이스에 저장하고, 생성한 상품의 pno 값을 얻습니다.
        Product1 savedProduct = productRepository.findById(savedPno)
                .orElseThrow(() -> new NoSuchElementException("생성한 상품을 찾을 수 없습니다. pno=" + savedPno));

        // 상품과 카테고리 연관 관계 설정
        savedProduct.setCategory(category);

        // 실제로는 상품 정보와 옵션들을 데이터베이스에 저장하는 로직을 구현해야 합니다.
        // 이후에 productRepository.save(savedProduct)와 같은 로직을 사용하여 실제 데이터베이스에 저장합니다.

        return savedProduct.getPno(); // 생성한 상품의 pno를 반환합니다.
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


    public List<Product1> getProductsByCategory(Long cateno) {

        return productRepository.findAllByCategory(cateno);
    }

    private ProductDTO toDTO(Product1 product) {

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO readOneInCategory(Long cateno, Long pno) {
        return productRepository.findProductInCategory(cateno, pno)
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .orElse(null);
    }

    @Override
    public Long setProduct(ProductDTO productDTO) {
        // 상품 엔티티 생성
        Product1 product = Product1.builder()
                .pname(productDTO.getPname())
                .pprice(productDTO.getPprice())
                .build();

        // 옵션 엔티티 생성 및 상품과 연관 관계 설정
        List<OptionsDTO> optionsDTOList = productDTO.getOptions();
        if (optionsDTOList != null && !optionsDTOList.isEmpty()) {
            for (OptionsDTO optionsDTO : optionsDTOList) {
                Options option = Options.builder()
                        .oname(optionsDTO.getOname())
                        .oprice(optionsDTO.getOprice())
                        .product1(product)
                        .build();
                // 옵션 엔티티를 저장합니다.
                optionsRepository.save(option);
            }
        }



        Product1 savedProduct = productRepository.save(product);

        return savedProduct.getPno(); // 생성한 상품의 pno를 반환합니다.
    }

//    @Override
//    public ProductDTO getProductDTOById(Long pno) {
//        Product1 product = productRepository.findById(pno)
//                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다."));
//        return toDTO(product);
//    }

    @Override
    public void modifyProduct(Long pno, ProductDTO productDTO) {
        Product1 existingProduct = productRepository.findById(pno)
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다. pno: " + pno));

        // 상품 정보 업데이트
        existingProduct.setPname(productDTO.getPname());
        existingProduct.setPprice(productDTO.getPprice());

        // 옵션 정보 업데이트
        List<OptionsDTO> optionsDTOList = productDTO.getOptions();
        List<Options> options = convertToOptionEntities(optionsDTOList, existingProduct);
        existingProduct.setOptions(options);

        // 파일 업로드 및 이미지 정보 설정
        List<MultipartFile> newImages = productDTO.getImages();
        if (!newImages.isEmpty()) {
            // 기존 이미지 삭제
            List<ProductImage> existingImages = existingProduct.getImages();
            List<String> existingImageFileNames = existingImages.stream()
                    .map(ProductImage::getFname)
                    .collect(Collectors.toList());
            fileUploader.removeFiles(existingImageFileNames);

            // 새로운 이미지 추가
            List<String> newImageFileNames = fileUploader.uploadFiles(newImages, true);
            List<ProductImage> newProductImages = newImageFileNames.stream()
                    .map(imageFileName -> new ProductImage(existingProduct, imageFileName))
                    .collect(Collectors.toList());
            existingProduct.setImages(newProductImages);
        }

        // 상품 엔티티를 데이터베이스에 저장합니다.
        productRepository.save(existingProduct);
    }

    private List<Options> convertToOptionEntities(List<OptionsDTO> optionsDTOList, Product1 existingProduct) {
        List<Options> options = new ArrayList<>();
        if (optionsDTOList != null && !optionsDTOList.isEmpty()) {
            for (OptionsDTO optionsDTO : optionsDTOList) {
                Options option = Options.builder()
                        .oname(optionsDTO.getOname())
                        .oprice(optionsDTO.getOprice())
                        .product1(existingProduct) // 기존 Product1 엔티티와 연관 관계 설정
                        .build();
                options.add(option);
            }
        }
        return options;
    }

}




