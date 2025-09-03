package com.hungnv.tourbooking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tours tours;

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Review() {
    }

    public Review(Long id, Customer customer, Tours tours, BigDecimal rating, String comment, LocalDateTime createdAt) {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Tours getTours() {
        return tours;
    }

    public void setTours(Tours tours) {
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
        return "Review{" +
            "id=" + id +
            ", user=" + customer +
            ", tours=" + tours +
            ", rating=" + rating +
            ", comment='" + comment + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }
}
