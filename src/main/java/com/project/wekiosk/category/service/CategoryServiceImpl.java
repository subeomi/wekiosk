package com.project.wekiosk.category.service;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.category.dto.CategoryDTO;
import com.project.wekiosk.category.repository.CategoryRepository;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.product.domain.ProductImage;
import com.project.wekiosk.product.repository.ProductRepository;
import com.project.wekiosk.util.FileUploader;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final FileUploader fileUploader = new FileUploader();

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
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
    public void registerCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCatename(categoryDTO.getCatename());

        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = mapToEntity(categoryDTO);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long cateno) {
        // 카테고리 조회
        Category category = categoryRepository.findById(cateno)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. cateno: " + cateno));

        // 카테고리에 속하는 상품들 조회
        List<Product> products = productRepository.findByCategory(category);

        // 상품들의 파일 삭제
        products.forEach(product -> {
            List<String> fileNames = product.getImages().stream()
                    .map(ProductImage::getFname)
                    .collect(Collectors.toList());
            fileUploader.removeFiles(fileNames);
        });

        // 카테고리와 카테고리에 속하는 상품들 삭제
        productRepository.deleteInBatch(products);
        categoryRepository.delete(category);
    }

    // mapToDTO: Category 엔티티 객체를 DTO 객체로 변환
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
