package com.project.wekiosk.order;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetail {

    private int quantity;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Product product;

}
