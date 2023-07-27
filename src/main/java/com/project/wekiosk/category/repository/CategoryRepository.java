package com.project.wekiosk.category.repository;

import com.project.wekiosk.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {


}
