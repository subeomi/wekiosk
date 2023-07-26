package com.project.wekiosk.product.domain;

import com.project.wekiosk.category.domain.Category;
import jakarta.persistence.*;
import lombok.*;

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

    private Long sno;

    public void addImage(String imagePath) {
        this.pimage = imagePath;
    }



}
