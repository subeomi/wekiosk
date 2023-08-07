package com.project.wekiosk.repositoryTests;

import com.project.wekiosk.domain.Store;
import com.project.wekiosk.order.domain.Orders;
import com.project.wekiosk.order.repository.OrdersRepository;
import com.project.wekiosk.product.domain.Product1;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class OrdersTests {

    @Autowired
    OrdersRepository repository;

    @Test
    @Commit
    @Transactional
    public void testInsert() {

        for (int i = 0; i < 10; i++) {

            Store store = Store.builder()
                    .sno(1L)
                    .build();

            Product1 product1 = Product1.builder()
                    .pno(2L)
                    .build();
            Product1 product2 = Product1.builder()
                    .pno(3L)
                    .build();

            Orders orders = Orders.builder()
                    .ostatus(0)
                    .store(store)
                    .build();

            orders.addOrderDetail(5+i, product1);
            orders.addOrderDetail(7+i, product2);

            repository.save(orders);

        }//end for

    }//end testInsert

    @Test
    public void testSelect(){

        log.info(repository.findById(5L));
        log.info("--------------------------");
        log.info(repository.findAll());

    }//end testSelect

    @Test
    public void testDelete(){

        Optional<Orders> result = repository.findById(4L);

        Orders orders = result.orElseThrow();

        orders.changeOstatus(1);

        repository.save(orders);

    }


}
