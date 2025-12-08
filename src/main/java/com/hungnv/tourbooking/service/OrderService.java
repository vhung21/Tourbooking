package com.hungnv.tourbooking.service;

import com.hungnv.tourbooking.domain.Order;
import com.hungnv.tourbooking.domain.PaymentStatus;
import com.hungnv.tourbooking.dto.OrderDTO;
import com.hungnv.tourbooking.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public List<Order> getOrdersByTourId(Long tourId) {
        return orderRepository.findByTourId(tourId);
    }

    @Transactional
    public Order createOrder(OrderDTO request) {
        Order order = new Order();
        order.setTourId(request.getTourId());
        order.setFullName(request.getFullName());
        order.setPhone(request.getPhone());
        order.setEmail(request.getEmail());
        order.setNumberOfPeople(request.getNumberOfPeople());
        order.setTotalPrice(request.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentStatus(PaymentStatus.PAID); // giả sử thanh toán thành công luôn

        return orderRepository.save(order);
    }
}

