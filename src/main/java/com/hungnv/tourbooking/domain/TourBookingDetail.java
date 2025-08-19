package com.hungnv.tourbooking.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "tour_booking_detail")
public class TourBookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_adult")
    private int numberOfAdults;

    @Column(name = "number_of_children")
    private int numberOfChildren;


    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonBackReference
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tours tours;

    public TourBookingDetail() {
    }

    public TourBookingDetail(int numberOfChildren, Booking booking, Tours tours, int numberOfAdults, Long id) {
        this.numberOfChildren = numberOfChildren;
        this.booking = booking;
        this.tours = tours;
        this.numberOfAdults = numberOfAdults;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Tours getTours() {
        return tours;
    }

    public void setTours(Tours tours) {
        this.tours = tours;
    }

    @Override
    public String toString() {
        return "TourBookingDetail{" +
            "id=" + id +
            ", numberOfAdults=" + numberOfAdults +
            ", numberOfChildren=" + numberOfChildren +
            ", bookingId=" + (booking != null ? booking.getId() : null) +
            ", tourId=" + (tours != null ? tours.getId() : null) +
            '}';
    }
}
