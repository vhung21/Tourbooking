package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
