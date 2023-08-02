package com.project.wekiosk.product.domain;

import com.project.wekiosk.category.domain.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
public class Product1 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pno;
    private String pname;
    private Long pprice;

    private String pimage;

    @ManyToOne
    @JoinColumn(name = "cateno") // 외래키 지정
    private Category category;

    private boolean delFlag;

    private Long sno;

    @ElementCollection(fetch = FetchType.LAZY )
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();


    public void addImage(String name){
        ProductImage pImage = ProductImage.builder().fname(name)
                .ord(images.size()).build();
        images.add(pImage);
    }

    // Category 객체를 설정하는 메서드
    public void setCategory(Category category) {

        this.category = category;
    }

    public void changeDel(boolean delFlag) {

        this.delFlag = delFlag;
    }

    public void changePname(String pname){
        this.pname = pname;
    }

    public void changePprice(Long pprice){
        this.pprice = pprice;
    }

    public void clearImage(){
        images.clear();
    }

}
