package com.hungnv.tourbooking.service;

import com.hungnv.tourbooking.domain.Review;
import com.hungnv.tourbooking.payload.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.security.Principal;


public interface ReviewService {
    ResponseEntity<ResponseObject> getAllReviewsByTourId(Long tourId);

    ResponseEntity<ResponseObject> createReview(Long tourId, Review review, Long customerId);

    ResponseEntity<ResponseObject> updateReview(Long reviewId, Review updatedReview, Long customerId);
//
//    ResponseEntity<ResponseObject> deleteReview(Long tourId, Review review, Principal principal);

}
