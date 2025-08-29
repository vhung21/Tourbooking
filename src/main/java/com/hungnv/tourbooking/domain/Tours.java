package com.hungnv.tourbooking.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "Tours")
@Entity
public class Tours {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tour_name", unique = true, nullable = false, length = 300)
    private String tourName;

    @Column(name = "description", nullable = false, length = 300)
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "location", nullable = false, length = 300)
    private String location;

    @Column(name = "transportation", nullable = false, length = 300)
    private String transportation;

    @Column(name = "image_url", nullable = false, length = 300)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "review_count")
    private Integer reviewCount;

    public Tours() {
    }

    public Tours(Long id, String tourName, Integer reviewCount, String description, LocalDate startDate, BigDecimal price, LocalDate endDate, String location, String transportation, String imageUrl, User createdBy, Double averageRating) {
        this.id = id;
        this.tourName = tourName;
        this.reviewCount = reviewCount;
        this.description = description;
        this.startDate = startDate;
        this.price = price;
        this.endDate = endDate;
        this.location = location;
        this.transportation = transportation;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.averageRating = averageRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    @Override
    public String toString() {
        return "Tours{" +
            "id=" + id +
            ", tourName='" + tourName + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", location='" + location + '\'' +
            ", transportation='" + transportation + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", createdBy=" + createdBy +
            '}';
    }
}
