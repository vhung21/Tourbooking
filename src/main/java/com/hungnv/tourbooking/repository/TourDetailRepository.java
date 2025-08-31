package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.ToursDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface TourDetailRepository extends JpaRepository<ToursDetail, Long> {
    Optional<ToursDetail> findByTours_Id(Long tourId);
}
