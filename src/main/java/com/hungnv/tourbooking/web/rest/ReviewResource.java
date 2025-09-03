package com.hungnv.tourbooking.web.rest;

import com.hungnv.tourbooking.domain.Review;
import com.hungnv.tourbooking.payload.ResponseObject;
import com.hungnv.tourbooking.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewResource {
    @Autowired
    ReviewService reviewService;

    @PostMapping("/{tourId}/reviews")
    public ResponseEntity<ResponseObject> createReview(
        @PathVariable Long tourId,
        @RequestParam Long customerId,
        @RequestBody Review review
    ) {
        return reviewService.createReview(tourId, review, customerId);
    }

    @GetMapping("/{tourId}/reviews")
    public ResponseEntity<ResponseObject> getAllReviewsByTourId(@PathVariable Long tourId) {
        return reviewService.getAllReviewsByTourId(tourId);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ResponseObject> updateReview(
        @PathVariable Long reviewId,
        @RequestBody Review review,
        @RequestParam Long customerId) {
        return reviewService.updateReview(reviewId,review,customerId);
    }
}
