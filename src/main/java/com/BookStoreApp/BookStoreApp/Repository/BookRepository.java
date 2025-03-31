package com.BookStoreApp.BookStoreApp.Repository;

import com.BookStoreApp.BookStoreApp.Domain.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByName(String name);
    List<Book> findByAuthorName(String authorName);
    Optional<Book> findById(UUID uuid);

    @Query("SELECT b FROM Book b WHERE " +
            "(:name IS NULL OR b.name LIKE %:name%) AND " +
            "(:authorName IS NULL OR b.authorName LIKE %:authorName%) AND " +
            "(:categoryName IS NULL OR b.categoryName LIKE %:categoryName%) AND " +
            "(:isAvailable IS NULL OR b.isAvailable = :isAvailable)")
    List<Book> filterBooks(
            @Param("name") String name,
            @Param("categoryName") String categoryName,
            @Param("authorName") String authorName,
            @Param("isAvailable") Boolean isAvailable

    );
}
