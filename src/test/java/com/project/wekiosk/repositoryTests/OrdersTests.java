package com.project.wekiosk.repositoryTests;

import com.project.wekiosk.domain.Store;
import com.project.wekiosk.order.OrderDetail;
import com.project.wekiosk.order.Orders;
import com.project.wekiosk.order.OrdersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

            Orders orders = Orders.builder()
                    .ostatus(0)
                    .store(store)
                    .build();

            orders.addOrderDetail(5+i);
            orders.addOrderDetail(7+i);

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
