package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.Tours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tours, Long> {
    Optional<Tours> findById(Long id);

    boolean existsByTourName(String tourName);
}
