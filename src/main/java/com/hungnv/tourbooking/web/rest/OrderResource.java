package com.hungnv.tourbooking.web.rest;

import com.hungnv.tourbooking.domain.Order;
import com.hungnv.tourbooking.dto.OrderDTO;
import com.hungnv.tourbooking.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderResource {
    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO request) {
        Order created = orderService.createOrder(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOne(@PathVariable Long id) {
        return orderService.getOrderById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tour/{tourId}")
    public ResponseEntity<List<Order>> getByTour(@PathVariable Long tourId) {
        return ResponseEntity.ok(orderService.getOrdersByTourId(tourId));
    }
}
