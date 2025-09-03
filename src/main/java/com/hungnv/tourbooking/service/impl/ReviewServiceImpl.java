package com.hungnv.tourbooking.service.impl;

import com.hungnv.tourbooking.domain.Customer;
import com.hungnv.tourbooking.domain.Review;
import com.hungnv.tourbooking.domain.Tours;
import com.hungnv.tourbooking.dto.CustomerDTO;
import com.hungnv.tourbooking.dto.ReviewDTO;
import com.hungnv.tourbooking.dto.TourDTO;
import com.hungnv.tourbooking.mapper.ReviewMapper;
import com.hungnv.tourbooking.payload.ResponseObject;
import com.hungnv.tourbooking.repository.CustomerRepository;
import com.hungnv.tourbooking.repository.ReviewRepository;
import com.hungnv.tourbooking.repository.TourRepository;
import com.hungnv.tourbooking.repository.UserRepository;
import com.hungnv.tourbooking.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    public ResponseEntity<ResponseObject> createReview(Long tourId, Review review, Long customerId) {
        try {
            Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
            Tours tours = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
            boolean exists = reviewRepository.existsByToursAndCustomer(tours, customer);
            if (exists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Customer already reviewed this tour", null)
                );
            }
            review.setCustomer(customer);
            review.setTours(tours);
            review.setCreatedAt(LocalDateTime.now());
            Review savedReview = reviewRepository.save(review);

            List<Review> reviews = reviewRepository.getAllReviewByToursId(tourId);
            if (!reviews.isEmpty()) {
                BigDecimal sum = reviews.stream().map(Review::getRating).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal avg = sum.divide(
                    BigDecimal.valueOf(reviews.size()),
                    2, RoundingMode.HALF_UP);
                tours.setAverageRating(avg);
                tours.setReviewCount(reviews.size());
            } else {
                tours.setAverageRating(BigDecimal.valueOf(0.0));
            }
            tourRepository.save(tours);


            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId(savedReview.getId());
            reviewDTO.setRating(savedReview.getRating());
            reviewDTO.setComment(savedReview.getComment());
            reviewDTO.setCreatedAt(savedReview.getCreatedAt());

            TourDTO tourDTO = new TourDTO();
            tourDTO.setId(tours.getId());
            tourDTO.setTourName(tours.getTourName());
            tourDTO.setDescription(tours.getDescription());
            tourDTO.setPrice(tours.getPrice());
            tourDTO.setAverageRating(tours.getAverageRating());
            reviewDTO.setTours(tourDTO);

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setFullName(customer.getFullName());
            reviewDTO.setCustomer(customerDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("success", "Review added successfully", reviewDTO)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("failed", e.getMessage(), null)
            );
        }
    }

    public ResponseEntity<ResponseObject> getAllReviewsByTourId(Long tourId) {
        try {
            List<Review> reviews = reviewRepository.getAllReviewByToursId(tourId);
            List<ReviewDTO> reviewDTOS = new ArrayList<>();
            for (Review review : reviews) {
                reviewDTOS.add(reviewMapper.toDTO(review));
            }
            return ResponseEntity.ok(
                new ResponseObject("success", "Get reviews successfully", reviewDTOS)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("failed", e.getMessage(), null)
            );
        }
    }

    public ResponseEntity<ResponseObject> updateReview(Long reviewId, Review updatedReview, Long customerId) {
        try {
            Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

            Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

            Tours tours = tourRepository.findById(existingReview.getTours().getId())
                .orElseThrow(() -> new RuntimeException("Tour not found"));

            if (!existingReview.getCustomer().getId().equals(customer.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ResponseObject("failed", "You can only update your own review", null)
                );
            }

            existingReview.setComment(updatedReview.getComment());
            existingReview.setRating(updatedReview.getRating());
            existingReview.setCreatedAt(LocalDateTime.now());

            Review updatedReviewNew = reviewRepository.save(existingReview);

            List<Review> reviews = reviewRepository.getAllReviewByToursId(existingReview.getTours().getId());
            if (!reviews.isEmpty()) {
                BigDecimal sum = reviews.stream().map(Review::getRating).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal avg = sum.divide(
                    BigDecimal.valueOf(reviews.size()),
                    2, RoundingMode.HALF_UP);
                tours.setAverageRating(avg);
                tours.setReviewCount(reviews.size());
            }
            tourRepository.save(tours);


            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId(updatedReviewNew.getId());
            reviewDTO.setRating(updatedReviewNew.getRating());
            reviewDTO.setComment(updatedReviewNew.getComment());
            reviewDTO.setCreatedAt(updatedReviewNew.getCreatedAt());

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setFullName(customer.getFullName());
            reviewDTO.setCustomer(customerDTO);

            return ResponseEntity.ok(
                new ResponseObject("success", "Review updated successfully", reviewDTO)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("failed", e.getMessage(), null)
            );
        }
    }

}
