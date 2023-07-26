package com.project.wekiosk.category.service;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.category.dto.CategoryDTO;
import com.project.wekiosk.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long cateno) {
        Category category = categoryRepository.findById(cateno)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToDTO(category);
    }

    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        Category category = mapToEntity(categoryDTO);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = mapToEntity(categoryDTO);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long cateno) {
        categoryRepository.deleteById(cateno);
    }

    // mapToDTO Category 엔티티 객체를 DTO 객체로 변환
    private CategoryDTO mapToDTO(Category category) {
        return CategoryDTO.builder()
                .cateno(category.getCateno())
                .catename(category.getCatename())
                .sno(category.getSno())
                .build();
    }

    private Category mapToEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .cateno(categoryDTO.getCateno())
                .catename(categoryDTO.getCatename())
                .sno(categoryDTO.getSno())
                .build();
    }
}
