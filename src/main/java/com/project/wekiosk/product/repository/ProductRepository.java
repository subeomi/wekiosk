package com.project.wekiosk.product.repository;


import com.project.wekiosk.page.dto.PageRequestDTO;
import com.project.wekiosk.page.dto.PageResponseDTO;
import com.project.wekiosk.product.domain.Product1;
import com.project.wekiosk.product.dto.ProductListDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product1, Long>{


    @Query("select p.pname, p.pprice from Product1 p where p.pno = :pno")
    Product1 selectOne(@Param("pno") Long pno);


}
