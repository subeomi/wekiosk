package com.project.wekiosk.category.domain;

import com.project.wekiosk.product.domain.Product1;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cateno;
    private String catename;
    private Long sno;

    public Category(Long cateno) {
        this.cateno = cateno;
    }

    @OneToMany(mappedBy = "category") // 양방향 매핑을 위한 mappedBy 설정
    private List<Product1> products;
}
