package com.BookStoreApp.BookStoreApp.Service;

import com.BookStoreApp.BookStoreApp.Domain.Dto.BookDTO;
import com.BookStoreApp.BookStoreApp.Domain.Model.Book;
import com.BookStoreApp.BookStoreApp.Domain.Model.Category;
import com.BookStoreApp.BookStoreApp.Repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    public BookService(BookRepository bookRepository, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
    }

    /**
     * @return all books
     */
    public List<BookDTO> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(book ->
                new BookDTO(
                        book.getName(),
                        book.getAuthorName(),
                        book.getAvailable(),
                        book.getCategoryName()  // Extract category name
                )
        ).collect(Collectors.toList());
    }

    /**
     * @param id
     * @return specific book
     */
    public Optional<BookDTO> getBookById(UUID id) {
        return bookRepository.findById(id).map(book -> new BookDTO(
                book.getName(),
                book.getAuthorName(),
                book.getAvailable(),
                book.getCategoryName() // Correct way to get category name
        ));
    }

    /**
     *
     * @param authorName
     * @return specific book
     */
    public List<Book> getBookByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    /**
     * @param book
     * @return added book
     */
    public Book addBook(Book book) {
        // Ensure category exists
        Category category = categoryService.getCategoryByName(book.getCategoryName()).orElseThrow(() -> new RuntimeException("Category not found!"));;
        category.setBookNumber(category.getBookNumber()+1);
        //saving category
        categoryService.addCategory(category);
        return bookRepository.save(book);
    }

    /**
     *
     * @param bookId
     * @param updatedBook
     * @return updadet book
     */
    public Book updateBook(UUID bookId, Book updatedBook) {
        // Fetch the book and ensure it exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found!"));

        // Update basic fields
        book.setName(updatedBook.getName());
        book.setAuthorName(updatedBook.getAuthorName());
        book.setAvailable(updatedBook.getAvailable());

        // Update category if provided
        if (updatedBook.getCategoryName() != null) {
            Category categoryPrevious = categoryService.getCategoryByName(book.getCategoryName())
                    .orElseThrow(() -> new RuntimeException("Category not found!"));
            categoryPrevious.setBookNumber(categoryPrevious.getBookNumber()-1);

            Category category = categoryService.getCategoryByName(updatedBook.getCategoryName())
                    .orElseThrow(() -> new RuntimeException("Category not found!"));

            book.setCategoryName(category.getCategoryName());
            category.setBookNumber(category.getBookNumber()+1);
        }

        return bookRepository.save(book);
    }

    /**
     *
     * @param categoryName
     * @param authorName
     * @param isAvailable
     * @return filtered books
     */
    public List<BookDTO> filterBooks(String name, String categoryName, String authorName, Boolean isAvailable) {

        List<Book> bookList = bookRepository.filterBooks(name, categoryName, authorName, isAvailable);

        return bookList.stream().map(book ->
                new BookDTO(
                        book.getName(),
                        book.getAuthorName(),
                        book.getAvailable(),
                        book.getCategoryName()  // Extract category name
                )
        ).collect(Collectors.toList());
    }

    /**
     * Add predefined books to the book table
     */
    public void addBooks() {
        if (bookRepository.count() == 0) { // Check whether there are some added books or not

            // Find categories
            Optional<Category> fictionCategory = categoryService.getCategoryByName("Fiction");
            Optional<Category> scienceCategory = categoryService.getCategoryByName("Science");
            Optional<Category> historyCategory = categoryService.getCategoryByName("History");

            if (fictionCategory.isPresent() && scienceCategory.isPresent() && historyCategory.isPresent()) {
                Book book1 = new Book();
                book1.setName("The Great Gatsby");
                book1.setAuthorName("F. Scott Fitzgerald");
                book1.setAvailable(true);
                book1.setCategoryName(fictionCategory.get().getCategoryName());
                fictionCategory.get().setBookNumber(1);
                categoryService.addCategory(fictionCategory.get());

                Book book2 = new Book();
                book2.setName("A Brief History of Time");
                book2.setAuthorName("Stephen Hawking");
                book2.setAvailable(true);
                book2.setCategoryName(scienceCategory.get().getCategoryName());
                scienceCategory.get().setBookNumber(1);
                categoryService.addCategory(scienceCategory.get());

                Book book3 = new Book();
                book3.setName("Sapiens: A Brief History of Humankind");
                book3.setAuthorName("Yuval Noah Harari");
                book3.setAvailable(true);
                book3.setCategoryName(historyCategory.get().getCategoryName());
                historyCategory.get().setBookNumber(1);
                categoryService.addCategory(historyCategory.get());

                bookRepository.saveAll(List.of(book1, book2, book3));
                System.out.println("Books added successfully!");
            } else {
                System.out.println("Some categories are missing! Run category initialization first.");
            }
        }
    }
}
