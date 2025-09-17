package com.hungnv.tourbooking.repository;

import com.hungnv.tourbooking.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT d FROM Location d WHERE " +
        "LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND " +
        "d.isActive = true")
    List<Location> findActiveByNameContainingIgnoreCase(@Param("keyword") String keyword);
}

