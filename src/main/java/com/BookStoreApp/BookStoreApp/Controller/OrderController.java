package com.BookStoreApp.BookStoreApp.Controller;

import com.BookStoreApp.BookStoreApp.Domain.Dto.CategoryDTO;
import com.BookStoreApp.BookStoreApp.Domain.Dto.OrderDTO;
import com.BookStoreApp.BookStoreApp.Domain.Model.Order;
import com.BookStoreApp.BookStoreApp.Service.BookService;
import com.BookStoreApp.BookStoreApp.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders(){
        List<Order> orderServiceList = orderService.getAllOrders();

        return ResponseEntity.ok(orderServiceList.stream().map(order ->
                new OrderDTO(
                        order.getBookName()
                )
        ).collect(Collectors.toList()));
    }

    @PostMapping("/{bookId}")
    public Order createOrder(@PathVariable UUID bookId) {
        return orderService.createOrder(bookId);
    }

    @PutMapping("/{orderId}")
    public Order updateOrder(@PathVariable UUID orderId, @RequestParam UUID newBookId) {
        return orderService.updateOrder(orderId, newBookId);
    }

}
