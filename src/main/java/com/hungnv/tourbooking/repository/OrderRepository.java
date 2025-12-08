package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTourId(Long tourId);
}
