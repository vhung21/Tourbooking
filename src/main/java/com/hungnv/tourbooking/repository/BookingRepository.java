package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.Booking;
import com.hungnv.tourbooking.domain.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerId(Long customerId);
    List<Booking> findByStatus(BookingStatus status);
}
