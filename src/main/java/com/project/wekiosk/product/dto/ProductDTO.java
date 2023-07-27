package com.project.wekiosk.product.dto;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.category.dto.CategoryDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long pno;
    private String pname;
    private Long pprice;

    private Long cateno;
    private String pimage;


    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();



}
