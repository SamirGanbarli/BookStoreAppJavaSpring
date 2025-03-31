package com.BookStoreApp.BookStoreApp.Controller;

import com.BookStoreApp.BookStoreApp.Domain.Dto.BookDTO;
import com.BookStoreApp.BookStoreApp.Domain.Model.Book;
import com.BookStoreApp.BookStoreApp.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getMyBooks() {
        List<BookDTO> bookList = bookService.getAllBooks();

        return ResponseEntity.ok(bookList);
    }

    @PostMapping("/admin")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/admin/{id}")
    public Book updateBook(@PathVariable UUID id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @GetMapping("/filter")
    public List<BookDTO> filterBooks(@RequestParam(name = "name" ,required = false) String name,
                                     @RequestParam(name = "categoryName", required = false) String categoryName,
                                  @RequestParam(name = "authorName", required = false) String authorName,
                                  @RequestParam(name = "isAvailable", required = false) Boolean isAvailable) {
        return bookService.filterBooks(name, categoryName, authorName, isAvailable);
    }
}
