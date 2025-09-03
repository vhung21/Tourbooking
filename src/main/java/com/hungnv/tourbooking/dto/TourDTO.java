package com.hungnv.tourbooking.dto;

import com.hungnv.tourbooking.domain.Tours;
import com.hungnv.tourbooking.domain.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
public class TourDTO {
    private Long id;
    private String tourName;
    private String description;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String transportation;
    private String imageUrl;
    private User createdBy;
    private BigDecimal averageRating;
    private Integer reviewCount;

    private TourDetailDTO detail;
    private List<TourItineraryDTO> itineraries;
    private List<TourInclusionDTO> inclusions;

    public TourDTO() {
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

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public TourDetailDTO getDetail() {
        return detail;
    }

    public void setDetail(TourDetailDTO detail) {
        this.detail = detail;
    }

    public List<TourItineraryDTO> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<TourItineraryDTO> itineraries) {
        this.itineraries = itineraries;
    }

    public List<TourInclusionDTO> getInclusions() {
        return inclusions;
    }

    public void setInclusions(List<TourInclusionDTO> inclusions) {
        this.inclusions = inclusions;
    }
}
