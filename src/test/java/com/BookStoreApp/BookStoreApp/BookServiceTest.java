package com.BookStoreApp.BookStoreApp;

import com.BookStoreApp.BookStoreApp.Domain.Dto.BookDTO;
import com.BookStoreApp.BookStoreApp.Domain.Model.Book;
import com.BookStoreApp.BookStoreApp.Domain.Model.Category;
import com.BookStoreApp.BookStoreApp.Repository.BookRepository;
import com.BookStoreApp.BookStoreApp.Service.BookService;
import com.BookStoreApp.BookStoreApp.Service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Category category;
    private UUID bookId;

    @BeforeEach
    void setUp() {
        bookId = UUID.randomUUID();
        category = new Category(10, "Fiction");
        book = new Book(bookId, "The Great Gatsby", "F. Scott Fitzgerald", true, category.getCategoryName());
    }


    @Test
    void getBookById_ShouldReturnBook_WhenFound() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<BookDTO> foundBook = bookService.getBookById(bookId);

        assertTrue(foundBook.isPresent());
        assertEquals(book.getName(), foundBook.get().getName());
    }

    @Test
    void getBookById_ShouldReturnEmpty_WhenNotFound() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Optional<BookDTO> foundBook = bookService.getBookById(bookId);

        assertFalse(foundBook.isPresent());
    }

    @Test
    void updateBook_ShouldUpdateBook_WhenFound() {
        Book updatedBook = new Book(bookId, "Updated Title", "New Author", false, category.getCategoryName());
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(categoryService.getCategoryByName(any(String.class))).thenReturn(Optional.of(category));

        Book result = bookService.updateBook(bookId, updatedBook);

        assertNotNull(result);
        assertEquals("Updated Title", result.getName());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook_ShouldThrowException_WhenBookNotFound() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        Book updatedBook = new Book(bookId, "Updated Title", "New Author", false, category.getCategoryName());

        Exception exception = assertThrows(RuntimeException.class, () -> bookService.updateBook(bookId, updatedBook));
        assertEquals("Book not found!", exception.getMessage());
    }

    @Test
    void filterBooks_ShouldReturnFilteredBooks() {
        when(bookRepository.filterBooks("The Great Gatsby", "Fiction", "F. Scott Fitzgerald", true)).thenReturn(List.of(book));

        List<BookDTO> books = bookService.filterBooks("The Great Gatsby", "Fiction", "F. Scott Fitzgerald", true);

        assertEquals(1, books.size());
        verify(bookRepository, times(1)).filterBooks("The Great Gatsby" ,"Fiction", "F. Scott Fitzgerald", true);
    }
}

