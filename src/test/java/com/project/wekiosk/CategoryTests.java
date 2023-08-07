package com.project.wekiosk;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void insertCategory() {
        Category category = Category.builder()
                .catename("TestCategory")
                .sno(1L)
                .build();

        categoryRepository.save(category);

        Category foundCategory = categoryRepository.findById(1L).orElse(null);
        if (foundCategory == null) {
            System.out.println("Category not found");
            return;
        }

        if (!foundCategory.getCatename().equals("TestCategory")) {
            System.out.println("Category name mismatch");
            return;
        }

        if (!foundCategory.getSno().equals(100L)) {
            System.out.println("Sno mismatch");
            return;
        }

        System.out.println("Test passed");
    }

    @Test
    public void testDeleteCategory() {
        Category category = Category.builder()
                .cateno(1L)
                .catename("TestCategory")
                .sno(100L)
                .build();

        categoryRepository.save(category);

        categoryRepository.deleteById(1L);
        Category deletedCategory = categoryRepository.findById(1L).orElse(null);
        if (deletedCategory != null) {
            System.out.println("Delete failed");
            return;
        }

        System.out.println("Test passed");
    }
}