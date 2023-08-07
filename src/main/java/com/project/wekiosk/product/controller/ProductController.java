package com.project.wekiosk.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.wekiosk.option.dto.OptionsDTO;
import com.project.wekiosk.product.domain.Product1;
import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.service.ProductService;
import com.project.wekiosk.util.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category/")
@Log4j2
public class ProductController {

    private final ProductService productService;

    private final FileUploader uploader;


    @PostMapping(value = "{cateno}/products", consumes = "multipart/form-data")
    public ResponseEntity<ProductDTO> register(
            @PathVariable Long cateno,
            @ModelAttribute ProductDTO productDTO, List<MultipartFile> images) {
        try {
            productDTO.setCateno(cateno);

            List<OptionsDTO> optionsList = productDTO.getOptions();

            if (optionsList != null) {
                for (OptionsDTO optionsDTO : optionsList) {
                    optionsDTO.setPno(productDTO.getPno());
                }
            }

            // 사진 업로드 로직 추가
            List<String> uploadedImageFileNames = new ArrayList<>();

            if (images != null && !images.isEmpty()) {
                // 파일 업로더 메서드를 호출하여 이미지를 업로드하고 파일 이름들을 가져옴
                uploadedImageFileNames = uploader.uploadFiles(images, true);
            }

            Long registeredProduct = productService.register(productDTO);
            log.info(productDTO);

            // 등록된 상품 정보를 다시 응답
            return ResponseEntity.ok(productDTO);
        } catch (Exception e) {
            // 오류 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





    @DeleteMapping("products/{pno}/delete")
    public ResponseEntity<Map<String, Long>> removeProduct(@PathVariable Long pno) {
        productService.remove(pno);
        return ResponseEntity.ok(Collections.singletonMap("pno", pno));
    }

    @GetMapping("{cateno}/products")
    public ResponseEntity<List<Product1>> getProductsByCategory(@PathVariable Long cateno) {
        List<Product1> products = productService.getProductsByCategory(cateno);
        log.info(products);
        return ResponseEntity.ok(products);
    }


    @GetMapping("{cateno}/products/{pno}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable("cateno") Long cateno, @PathVariable("pno") Long pno) {
        log.info("-------------------------------------");
        log.info("Cateno-" + cateno);
        log.info("PNO-" + pno);
        log.info("-------------------------------------");
        ProductDTO productDTO = productService.readOneInCategory(cateno, pno);

        return ResponseEntity.ok(productDTO);
    }


    @PutMapping("{cateno}/products/{pno}/modify")
    public ResponseEntity<?> updateProduct(@PathVariable Long pno, @RequestBody ProductDTO productDTO) {
        try {

            System.out.println("pno: " + pno);
            System.out.println("productDTO: " + productDTO);

            productService.modifyProduct(pno, productDTO);
            return ResponseEntity.ok().build(); // 성공 응답
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build(); // 해당 상품을 찾지 못한 경우
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 기타 오류 발생 시
        }
    }
}
