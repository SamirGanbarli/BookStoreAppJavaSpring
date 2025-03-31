package com.BookStoreApp.BookStoreApp.Service;

import com.BookStoreApp.BookStoreApp.Domain.Model.Book;
import com.BookStoreApp.BookStoreApp.Domain.Model.Order;
import com.BookStoreApp.BookStoreApp.Repository.BookRepository;
import com.BookStoreApp.BookStoreApp.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    /**
     *
     * @return all orders
     */
    public List<Order> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();

        return orderList;
    }

    /**
     *
     * @param bookId
     * @return created order
     */
    public Order createOrder(UUID bookId) {
        // Validate that book exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found!"));

        Order order = new Order();
        order.setBookId(book.getId());
        order.setBookName(book.getName());
        Order outOrder = orderRepository.save(order);
        book.setAvailable(false);
        bookRepository.save(book);
        return outOrder;
    }

    /**
     *
     * @param orderId
     * @param newBookId
     * @return updated order
     */
    public Order updateOrder(UUID orderId, UUID newBookId) {
        return orderRepository.findById(orderId).map(order -> {
            Book newBook = bookRepository.findById(newBookId)
                    .orElseThrow(() -> new RuntimeException("Book not found!"));

            order.setBookId(newBook.getId());
            order.setBookName(newBook.getName());
            Order outOrder = orderRepository.save(order);
            newBook.setAvailable(false);
            return outOrder;
        }).orElseThrow(() -> new RuntimeException("Order not found!"));
    }
}
