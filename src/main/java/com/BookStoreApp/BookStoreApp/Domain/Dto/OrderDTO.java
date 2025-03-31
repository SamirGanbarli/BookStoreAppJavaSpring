package com.BookStoreApp.BookStoreApp.Domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;


public class OrderDTO {

    private String bookName;
    public OrderDTO(String bookName) {
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
