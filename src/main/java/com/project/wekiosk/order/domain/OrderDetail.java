package com.project.wekiosk.order.domain;

import com.project.wekiosk.product.domain.Product1;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
public class OrderDetail {

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product1 product;

}