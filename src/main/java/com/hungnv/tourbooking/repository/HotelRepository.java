package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.Hotel;
import com.hungnv.tourbooking.domain.Tours;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h ORDER BY h.rating DESC")
    List<Hotel> findTopHotel(PageRequest pageable);
}
