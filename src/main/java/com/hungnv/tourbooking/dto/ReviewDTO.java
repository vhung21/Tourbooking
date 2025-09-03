package com.hungnv.tourbooking.dto;

import com.hungnv.tourbooking.domain.Customer;
import com.hungnv.tourbooking.domain.Tours;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReviewDTO {
    private Long id;
    private CustomerDTO customer;
    private TourDTO tours;
    private BigDecimal rating;
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, CustomerDTO customer, TourDTO tours, BigDecimal rating, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.customer = customer;
        this.tours = tours;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public TourDTO getTours() {
        return tours;
    }

    public void setTours(TourDTO tours) {
        this.tours = tours;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
            "id=" + id +
            ", customer=" + customer +
            ", tours=" + tours +
            ", rating=" + rating +
            ", comment='" + comment + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }
}
