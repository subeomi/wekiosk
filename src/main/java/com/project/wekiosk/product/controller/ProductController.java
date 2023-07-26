package com.project.wekiosk.product.controller;

import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.service.ProductService;
import com.project.wekiosk.util.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/")
@Log4j2
public class ProductController {

    private final ProductService service;

    private final FileUploader uploader;


//    @PostMapping("")
//    public Map<String, Long> register(ProductDTO productDTO) {
//
//    }

}
