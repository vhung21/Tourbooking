package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.TourBookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourBookingDetailRepository extends JpaRepository<TourBookingDetail, Long> {
}
