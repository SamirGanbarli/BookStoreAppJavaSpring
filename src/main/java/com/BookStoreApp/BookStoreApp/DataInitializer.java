package com.BookStoreApp.BookStoreApp;

import com.BookStoreApp.BookStoreApp.Service.BookService;
import com.BookStoreApp.BookStoreApp.Service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    /**
     *This class is for initializing the predefined data for category and book table
     * 3 category and for each category one book
     */
    private final CategoryService categoryService;
    private final BookService bookService;

    public DataInitializer(CategoryService categoryService, BookService bookService) {
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {
        categoryService.addCategories(); // Ensuring categories exist first
        bookService.addBooks(); // Then adding books
    }
}
