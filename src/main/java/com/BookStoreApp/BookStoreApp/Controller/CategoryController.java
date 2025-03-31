package com.BookStoreApp.BookStoreApp.Controller;

import com.BookStoreApp.BookStoreApp.Domain.Dto.BookDTO;
import com.BookStoreApp.BookStoreApp.Domain.Dto.CategoryDTO;
import com.BookStoreApp.BookStoreApp.Domain.Model.Category;
import com.BookStoreApp.BookStoreApp.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        List<Category> categoryList = categoryService.getAllCategory();

        return ResponseEntity.ok(categoryList.stream().map(category ->
                new CategoryDTO(
                        category.getCategoryName(),
                        category.getBookNumber()// Extract category name
                )
        ).collect(Collectors.toList()));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getCategoryByName(@RequestParam(required = false) String categoryName){
        Category categoryList = categoryService.getCategoryByName(categoryName).orElseThrow(() -> new RuntimeException("Category not found!") );

        return ResponseEntity.ok(categoryList);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        categoryService.addCategory(category);
        return ResponseEntity.ok("Category Added");
    }

    @PutMapping("/admin/{id}")
    public Category updateCategory(@PathVariable UUID id, @RequestBody Category updatedCategory) {
        return categoryService.updateCategory(id, updatedCategory);
    }

    @DeleteMapping("admin/{id}")
    public String deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return "Category deleted successfully.";
    }
}
