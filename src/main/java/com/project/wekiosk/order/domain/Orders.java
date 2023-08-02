package com.project.wekiosk.order.domain;

import com.project.wekiosk.domain.Store;
import com.project.wekiosk.product.domain.Product1;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"store", "Details"})
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ono;

    private int ostatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderDetail> Details = new ArrayList<>();

    public void addOrderDetail(int count, Product1 product) {

        OrderDetail detail = OrderDetail.builder()
                .quantity(count)
                .product(product)
                .build();

        Details.add(detail);

    }

    public void clearDetails(){
        Details.clear();
    }

    public void changeOstatus(int ostatus){
        this.ostatus = ostatus;
    }

}
