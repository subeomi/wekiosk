package com.project.wekiosk.product.dto;

import lombok.*;

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
}
