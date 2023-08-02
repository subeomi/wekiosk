package com.project.wekiosk.category.repository;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.product.domain.Product1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByCateno(Long cateno);

}
