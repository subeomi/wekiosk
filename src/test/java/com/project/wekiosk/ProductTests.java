package com.project.wekiosk;//package com.project.wekiosk;

import com.project.wekiosk.product.domain.Product1;
import com.project.wekiosk.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static com.project.wekiosk.product.domain.QProduct1.product1;

@SpringBootTest
public class ProductTests {

    @Autowired
    ProductRepository repo;

     @Test
     public void testInsert(){

         for(int i = 0; i < 10; i++){
             Product1 product = Product1.builder()
                     .pname("Example Product")
                     .pprice(10000L)
                     .build();

             product.addImage(UUID.randomUUID().toString()+"_aaa.jpg");
             product.addImage(UUID.randomUUID().toString()+"_bbb.jpg");
             product.addImage(UUID.randomUUID().toString()+"_ccc.jpg");

             repo.save(product);
         }//end for
     }
    @Transactional
    @Test
    public void testRead(){

        Optional<Product1> result = repo.findById(1L);

        Product1 product = result.orElseThrow();

        System.out.println(product);
        System.out.println("--------------------------");
//        System.out.println(product.getImages());


    }
}
