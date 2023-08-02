package com.project.wekiosk.category.Controller;

import com.project.wekiosk.category.dto.CategoryDTO;
import com.project.wekiosk.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category/")
@RequiredArgsConstructor
@Log4j2
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{cateno}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long cateno){
        CategoryDTO category = categoryService.getCategoryById(cateno);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{cateno}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long cateno) {
        categoryService.deleteCategory(cateno);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
