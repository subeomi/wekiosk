package com.project.wekiosk.product.controller;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.category.repository.CategoryRepository;
import com.project.wekiosk.product.domain.Product1;
import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.repository.ProductRepository;
import com.project.wekiosk.product.service.ProductService;
import com.project.wekiosk.util.FileUploader;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/")
@Log4j2
public class ProductController {

    private final ProductService service;

    private final FileUploader uploader;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    @PostMapping("")
    public Map<String, Long> register(@RequestBody ProductDTO productDTO) {
        log.info("-------------------------");
        log.info(productDTO);

        List<String> fileNames = uploader.uploadFiles(productDTO.getFiles(), true);
        productDTO.setImages(fileNames);

        Long cateno = productDTO.getCateno();


        Product1 product = Product1.builder()
                .pname(productDTO.getPname())
                .pprice(productDTO.getPprice())
                .build();

        Category category = categoryRepository.findByCateno(cateno);


        product.setCategory(category);


        Long pno = service.register(productDTO);

        Map<String, Long> response = new HashMap<>();
        response.put("pno", pno);

        return response;
    }

    @DeleteMapping("{pno}")
    public Map<String, Long> delete(@PathVariable("pno") Long pno){

        log.info("PNO" + pno);
        service.remove(pno);
        return Map.of("result",pno);
    }


    @GetMapping("{pno}")
    public ProductDTO getOne(@PathVariable("pno")Long pno){

        log.info("PNO-" + pno);

        return service.readOne(pno);
    }


    @PostMapping("modify")
    public Map<String, Long> modify( ProductDTO productDTO){
        log.info("----------------------------");
        log.info(productDTO);

        if(productDTO.getFiles() != null && productDTO.getFiles().size() > 0) {

            List<String> uploadFileNames = uploader.uploadFiles(productDTO.getFiles(), true);

            List<String> oldFileNames = productDTO.getImages();

            uploadFileNames.forEach(fname -> oldFileNames.add(fname));
        }

        log.info("------------------------------");
        log.info(productDTO);

        return Map.of("result", 111L);


    }
}
