package com.BookStoreApp.BookStoreApp.Repository;

import com.BookStoreApp.BookStoreApp.Domain.Model.Book;
import com.BookStoreApp.BookStoreApp.Domain.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByCategoryName(String categoryName);
}
