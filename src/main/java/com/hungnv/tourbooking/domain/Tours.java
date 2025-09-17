package com.hungnv.tourbooking.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(mappedBy = "tours", cascade = CascadeType.ALL)
    private ToursDetail details;

    @OneToMany(mappedBy = "tours", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToursItinerary> itineraries = new ArrayList<>();

    @OneToMany(mappedBy = "tours", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToursInclusion> inclusions = new ArrayList<>();

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "departures", nullable = false, length = 300)
    private String departures;

    @Column(name = "destination", nullable = false, length = 300)
    private String destination;

    @Column(name = "transportation", nullable = false, length = 300)
    private String transportation;

    @Column(name = "image_url", nullable = false, length = 300)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @Column(name = "average_rating")
    private BigDecimal averageRating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @OneToMany(mappedBy = "tours", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Tours() {
    }

    public Tours(Long id, String tourName, BigDecimal price, String description, ToursDetail details, LocalDate startDate, LocalDate endDate, String departures, String destination, String transportation, String imageUrl, User createdBy, BigDecimal averageRating, Integer reviewCount) {
        this.id = id;
        this.tourName = tourName;
        this.price = price;
        this.description = description;
        this.details = details;
        this.startDate = startDate;
        this.endDate = endDate;
        this.departures = departures;
        this.destination = destination;
        this.transportation = transportation;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
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

    public String getDepartures() {
        return departures;
    }

    public void setDepartures(String departures) {
        this.departures = departures;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public ToursDetail getDetails() { return details; }
    public void setDetails(ToursDetail details) {
        this.details = details;

    }

    public List<ToursItinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<ToursItinerary> itineraries) {
        this.itineraries = itineraries;
    }

    public List<ToursInclusion> getInclusions() {
        return inclusions;
    }

    public void setInclusions(List<ToursInclusion> inclusions) {
        this.inclusions = inclusions;
    }

    @Override
    public String toString() {
        return "Tours{" +
            "id=" + id +
            ", tourName='" + tourName + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            ", details=" + details +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", departures='" + departures + '\'' +
            ", destination='" + destination + '\'' +
            ", transportation='" + transportation + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", createdBy=" + createdBy +
            ", averageRating=" + averageRating +
            ", reviewCount=" + reviewCount +
            '}';
    }
}
