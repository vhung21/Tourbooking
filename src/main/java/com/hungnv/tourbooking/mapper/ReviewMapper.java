package com.hungnv.tourbooking.mapper;

import com.hungnv.tourbooking.domain.Customer;
import com.hungnv.tourbooking.domain.Review;
import com.hungnv.tourbooking.domain.Tours;
import com.hungnv.tourbooking.dto.CustomerDTO;
import com.hungnv.tourbooking.dto.ReviewDTO;
import com.hungnv.tourbooking.dto.TourDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setCreatedAt(review.getCreatedAt());

        if (review.getCustomer() != null) {
            Customer customer = review.getCustomer();

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setFullName(customer.getFullName());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setPhone(customer.getPhone());
            customerDTO.setAddress(customer.getAddress());

            reviewDTO.setCustomer(customerDTO);
        } else {
            reviewDTO.setCustomer(null);
        }

        if (review.getTours() != null) {
            Tours tours = review.getTours();

            TourDTO tourDTO = new TourDTO();
            tourDTO.setId(tours.getId());
            tourDTO.setTourName(tours.getTourName());
            tourDTO.setDescription(tours.getDescription());
            tourDTO.setPrice(tours.getPrice());
            tourDTO.setStartDate(tours.getStartDate());
            tourDTO.setEndDate(tours.getEndDate());
            tourDTO.setDepartures(tours.getDepartures());
            tourDTO.setDestination(tours.getDestination());
            tourDTO.setTransportation(tours.getTransportation());
            tourDTO.setImageUrl(tours.getImageUrl());
            tourDTO.setAverageRating(tours.getAverageRating());
            tourDTO.setReviewCount(tours.getReviewCount());

            reviewDTO.setTours(tourDTO);
        } else {
            reviewDTO.setTours(null);
        }

        return reviewDTO;
    }
}
