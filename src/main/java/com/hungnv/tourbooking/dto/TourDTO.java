package com.hungnv.tourbooking.dto;

import com.hungnv.tourbooking.domain.Tours;
import com.hungnv.tourbooking.domain.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
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

    public TourDTO(Tours tours) {
        this.id = tours.getId();
        this.tourName = tours.getTourName();
        this.description = tours.getDescription();
        this.price = tours.getPrice();
        this.startDate = tours.getStartDate();
        this.endDate = tours.getEndDate();
        this.location = tours.getLocation();
        this.transportation = tours.getTransportation();
        this.imageUrl = tours.getImageUrl();
        this.createdBy = tours.getCreatedBy();
    }

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
}
