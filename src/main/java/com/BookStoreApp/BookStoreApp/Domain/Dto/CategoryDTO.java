package com.BookStoreApp.BookStoreApp.Domain.Dto;

import com.BookStoreApp.BookStoreApp.Domain.Model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private String categoryName;
    private Integer bookNumber;

    public CategoryDTO(String categoryName, Integer bookNumber) {
        this.categoryName = categoryName;
        this.bookNumber = bookNumber;
    }

    public Integer getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(Integer bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
