package com.project.wekiosk.product.repository;


import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.product.domain.Product1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product1, Long>{


    Product1 save(Product1 product);

    @Query("select p.pname, p.pprice from Product1 p where p.pno = :pno")
    Product1 selectOne(@Param("pno") Long pno);

    @Query("SELECT p FROM Product1 p WHERE p.category.cateno = :cateno")
    List<Product1> findAllByCategory(Long cateno);

    List<Product1> findByCategory(Category category);

    @Query("SELECT p FROM Product1 p WHERE p.category.cateno = :cateno AND p.pno = :pno")
    Optional<Product1> findProductInCategory(@Param("cateno") Long cateno, @Param("pno") Long pno);
}
