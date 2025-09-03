package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.Customer;
import com.hungnv.tourbooking.domain.Review;
import com.hungnv.tourbooking.domain.Tours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByToursId(Long tourId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.tours.id = :tourId")
    Double getAverageRatingByTourId(Long tourId);

    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.tours.id = :tourId GROUP BY r.rating")
    List<Object[]> getRatingDistribution(Long tourId);

    List<Review> findByTours(Tours tours);

    boolean existsByToursAndCustomer(Tours tours, Customer customer);

    List<Review> getAllReviewByToursId(Long tours_id);

    boolean existsByCustomer(Customer customer);
}
