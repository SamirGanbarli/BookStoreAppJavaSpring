package com.BookStoreApp.BookStoreApp.Domain.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.util.UUID;

@RequiredArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "category_name")
    private String categoryName;



    public Book(UUID id, String name, String authorName, Boolean isAvailable, String categoryName) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.isAvailable = isAvailable;
        this.categoryName = categoryName;
    }

    public Book() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setCategoryName(String categoryNama) {
        this.categoryName = categoryNama;
    }
}
