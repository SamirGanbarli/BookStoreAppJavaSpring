package com.BookStoreApp.BookStoreApp.Service;

import com.BookStoreApp.BookStoreApp.Domain.Model.Category;
import com.BookStoreApp.BookStoreApp.Repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     *
     * @param name
     * @return specific category
     */
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    /**
     *
     * @return all categories
     */
    public List<Category> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList;
    }

    /**
     *
     * @param category
     * @return added Category
     */
    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    /**
     *
     * @param categoryId
     * @param updatedCategory
     * @return updated category
     */
    @Transactional
    public Category updateCategory(UUID categoryId, Category updatedCategory) {
        Optional<Category> existingCategoryOpt = categoryRepository.findById(categoryId);

        if (existingCategoryOpt.isPresent()) {
            Category existingCategory = existingCategoryOpt.get();
            existingCategory.setCategoryName(updatedCategory.getCategoryName());
            existingCategory.setBookNumber(updatedCategory.getBookNumber());

            return categoryRepository.save(existingCategory);
        } else {
            throw new RuntimeException("Category not found with ID: " + categoryId);
        }
    }

    public void deleteCategory(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found with ID: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    /**
     * Add predefined categories to category table
     */
    public void addCategories() {
        if (categoryRepository.count() == 0) { // Ensure categories are not duplicated
            Category fiction = new Category();
            fiction.setCategoryName("Fiction");
            fiction.setBookNumber(0);

            Category science = new Category();
            science.setCategoryName("Science");
            science.setBookNumber(0);

            Category history = new Category();
            history.setCategoryName("History");
            history.setBookNumber(0);

            categoryRepository.saveAll(List.of(fiction, science, history));
            System.out.println("Categories added successfully!");
        }
    }
}
