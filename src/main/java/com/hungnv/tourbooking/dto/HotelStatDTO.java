package com.hungnv.tourbooking.dto;

import java.io.Serializable;

public class HotelStatDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long bookingCount;

    public HotelStatDTO() {}

    public HotelStatDTO(Long id, String name, Long bookingCount) {
        this.id = id;
        this.name = name;
        this.bookingCount = bookingCount;
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
}
