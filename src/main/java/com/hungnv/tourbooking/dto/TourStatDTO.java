package com.hungnv.tourbooking.dto;

import java.io.Serializable;

public class TourStatDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long bookingCount;
    private Double revenue;

    public TourStatDTO() {}

    public TourStatDTO(Long id, String name, Long bookingCount, Double revenue) {
        this.id = id;
        this.name = name;
        this.bookingCount = bookingCount;
        this.revenue = revenue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(Long bookingCount) {
        this.bookingCount = bookingCount;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }
}
