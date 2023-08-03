package com.project.wekiosk.option.domain;

import com.project.wekiosk.product.domain.Product1;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Entity
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ord")
    private Long ord;

    private String oname;
    private Long oprice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno", referencedColumnName = "pno")
    private Product1 product1;

    public void setPno(Long pno) {
    }
}