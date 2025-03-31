package com.BookStoreApp.BookStoreApp.Domain.Dto;

import com.BookStoreApp.BookStoreApp.Domain.Model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

public class BookDTO {
    private String name;
    private String authorName;
    private Boolean isAvailable;
    private String categoryName;

    public BookDTO(String name, String authorName, Boolean isAvailable, String categoryName) {
        this.name = name;
        this.authorName = authorName;
        this.isAvailable = isAvailable;
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
