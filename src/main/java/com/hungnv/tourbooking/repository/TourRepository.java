package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.Tours;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tours, Long> {
    Optional<Tours> findById(Long id);

    boolean existsByTourName(String tourName);

    List<Tours> findByTourNameContainingIgnoreCase(String keyword);

    @Query("SELECT t FROM Tours t ORDER BY (t.averageRating * LOG(t.reviewCount + 1)) DESC")
    List<Tours> findTopToursByWeightedScore(PageRequest pageable);

    boolean existsByTourNameAndIdNot(String tourName, Long id);
}
