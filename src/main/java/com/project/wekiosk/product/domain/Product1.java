package com.project.wekiosk.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.option.domain.Options;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
public class Product1 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pno")
    private Long pno;
    private String pname;
    private Long pprice;

    @ManyToOne
    @JoinColumn(name = "cateno", referencedColumnName = "cateno")
    @JsonIgnore
    private Category category;

    private Long sno;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product1", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Options> options = new ArrayList<>();


    public void addImage(String name) {
        ProductImage pImage = ProductImage.builder().fname(name)
                .ord(images.size()).build();
        images.add(pImage);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addOption(Options option) {
        options.add(option);
    }

    public void setCateno(Long cateno) {
    }
}
