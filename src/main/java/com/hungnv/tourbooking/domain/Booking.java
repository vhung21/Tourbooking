package com.hungnv.tourbooking.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TourBookingDetail> tourBookingDetails = new ArrayList<>();

    @Column(name = "total_price", precision = 19, scale = 0)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalPrice;

    @Column(name = "booking_type")
    private String bookingType;

    @Enumerated(EnumType.STRING)
    private BookingStatus status; // PENDING, PAID, CANCELED

    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = BookingStatus.PENDING;
        }
    }

    public Booking() {
    }

    public Booking(Long id, List<TourBookingDetail> tourBookingDetails, Customer customer, BigDecimal totalPrice, String bookingType, BookingStatus status, LocalDateTime createdAt, LocalDateTime paidAt) {
        this.id = id;
        this.tourBookingDetails = tourBookingDetails;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.bookingType = bookingType;
        this.status = status;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
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

    public List<TourBookingDetail> getTourBookingDetails() {
        return tourBookingDetails;
    }

    public void setTourBookingDetails(List<TourBookingDetail> tourBookingDetails) {
        this.tourBookingDetails = tourBookingDetails;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + id +
            ", totalPrice=" + totalPrice +
            ", bookingType='" + bookingType + '\'' +
            ", status=" + status +
            ", createdAt=" + createdAt +
            ", paidAt=" + paidAt +
            '}';
    }
}
